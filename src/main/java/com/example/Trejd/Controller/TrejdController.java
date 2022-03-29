package com.example.Trejd.Controller;


import com.example.Trejd.Review;
import com.example.Trejd.Service.TrejdService;
import com.example.Trejd.Trejd;

import com.example.Trejd.OfferTrejd;
import com.example.Trejd.OrderTrejd;
import com.example.Trejd.Service.TrejdService;
import com.example.Trejd.Skill;
import com.example.Trejd.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpSession;
import java.util.List;


@Controller
public class TrejdController {

    // todo
    @Autowired
    TrejdService service;

    @GetMapping("/")
    public String showStartPage() {


        return "home";
    }

  @GetMapping("/orders")
    public String getOrderPage(Model model) {
      model.addAttribute("users", service.findAllUsers());
      return "orderlist";
  }

  @GetMapping("/maketrejd/{id}")

    public String makeTrejdPage(Model model, @PathVariable Long id) {
      User user = service.getUserById(id);
      List<Review> reviews = service.getReviewsOnUser(user);
      model.addAttribute("user", user);
      model.addAttribute("reviews", reviews);
      model.addAttribute("skills", service.getUserSkills(id));
      return "maketrejd";
  }

    //spara undan medelvärdet i databasen så vi kan söka på högst betyg --> nice to have

//    @PostMapping("/")
//    public String checkLogin(@RequestParam String email, @RequestParam String password,HttpSession session) {
//        User user = service.getUser(email, password);
//
//        if (user != null) {
//            System.out.println("test");
//            session.setAttribute("user", user);
//            return "my-page";
//        } else {
//            System.out.println("No such user!");
//            return "home";
//        }
//    }

    //-- Here we also need the postmapping for create user click on the button should send us to create new user page -->


   // @PostMapping("/test/{name}")
    //public String getLoginPage(@RequestParam String email, @PathVariable String name, Model model, HttpSession session) {

        //Invärden från användare
        // @RequestParam /test?email="twana@test.se" <----
        // @PathVariable /test/Twana ex. pagination eller ett id

        //Model följer bara med till HTML-sidan
        //Model -> Spara till view (HTML/Thymeleaf)
        //model.addAttribute(user);
        //model.getAttribute(user);

        // Sessions följer med under medans användaren är inloggad
        //session.getAttribute(user) -> Vid login. setSession;
        //session.setAttribute(user);

//        return "home";
//    }

    @GetMapping("/login")
    public String getLoginPage2() {
        return "login";
    }

    @PostMapping("/login")
    public String checkLogin(@RequestParam String email, @RequestParam String password, HttpSession session) {
        User user = service.getUser(email, password);

        if (user != null) {
            session.setAttribute("user", user);
            return "my-page";
        } else {
            System.out.println("No such user!");
            return "login";
        }
    }

//        return  "home";
//    }






    @GetMapping("/my-page")
    public String getMyPage() {
      //User user = (User)session.getAttribute("user");
      // if no user restrict view.
        return "my-page";
    }


    @GetMapping("/info")
    public String getInfo() {
        return "info";
    }

    @GetMapping("/info2")
    public String getInfo2() {
        return "info2";
    }

    @GetMapping("/info3")
    public String getInfo3() {
        return "info3";
    }

    //Anropas när båda har avslutat trejden. När utföraren är klar och kund godkänt så skickas man till reviewn.

    @GetMapping("/trejdInfo/{id}") //info om specifikt trejd-id
    public String getTrejdInfo(@PathVariable Long id, Model model, HttpSession session) {
        Trejd trejd = service.getTrejd(id); //vi plockar ut hela trejden från databasen baserat på id
        User user = (User) session.getAttribute("user"); //vi plockar ut den inloggade användaren. getAttribute returnerar ett objekt. Den vet inte vad det är för typ, därför berättar vi det för den med (User). Man "castar".
        if (user == null) { // dubbelkollar att någon är inloggad, är man inte det så redirectas man till loginsidan.
            return "redirect:/login";
        }

        if (trejd.getPerformer().getId() == user.getId()
                || trejd.getOrder().getUser().getId() == user.getId()) { //vi kollar om den inloggade användaren är involverad i trejden
            boolean writtenByPerformer = trejd.getPerformer().getId() == user.getId();
            //vi går in i trejden. Vi kollar dens offer, sen kollar vi på offers user, sen kollar vi på users id, sen
            //kollar vi om det är den som är inloggad
            //vi går in i trejden. Vi kollar dens order, sen kollar vi på orderns user, sen kollar vi på users id, sen
            //kollar vi om det är den som är inloggad. vi kollar om den som är inloggad är den som köpt eller den som erbjudit tjänsten

            model.addAttribute("trejd", trejd); //vi lägger in trejden i modellen så att viewn kommer åt den
            model.addAttribute("writtenByPerformer", writtenByPerformer);
            return "trejdInfo"; //vi kommer in på trejdInfo-sidan
        } else {
            return "redirect:/";//har personen inte tillgång till denna sida redirectas den till startsidan
        }
    }

    @PostMapping("/trejdInfo/{trejdId}/addReview/")
    public String addReview(@PathVariable Long trejdId, @RequestParam String description, @RequestParam int rating, HttpSession session) {
        Trejd trejd = service.getTrejd(trejdId);
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/";
        }

        if (trejd.getPerformer().getId() == user.getId()
                || trejd.getOrder().getUser().getId() == user.getId()) {
            boolean writtenByPerformer = trejd.getPerformer().getId() == user.getId();
            Review review = new Review(trejd.getOrder().getUser(), trejd.getPerformer(), trejd, description, rating, writtenByPerformer);
            service.createReview(review);

            User reviewee = writtenByPerformer
                    ? trejd.getOrder().getUser()
                    : trejd.getPerformer();

            List<Review> reviews = service.getReviewsOnUser(reviewee);
            Double averageRating = reviews.stream().mapToDouble(r -> r.getRating()).average().orElse(0.0);
            if (averageRating != 0) {
                reviewee.setRating(averageRating);
                service.saveUser(reviewee);
            }
            return "tack";
        }

        return "redirect:/";
    }

    @PostMapping("/my-page")
    public String createAnOrder(@RequestParam String location, @RequestParam User user, @RequestParam Skill skill, HttpSession session) {
        OrderTrejd order = new OrderTrejd(location, user, skill);
        service.createOrder(order);
        return "orderlist";

    }
//    @PostMapping("/my-page")
//   public String uppdateMyInfo(@RequestParam String firstName,@RequestParam String lastName,@RequestParam String password,@RequestParam String email) {
//        //skills???
//        User user = new User(firstName, lastName, password, email);
//        service.createUser(user);
//        return "my-page";
//    }

  

}
