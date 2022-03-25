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

import org.springframework.validation.BindingResult;

import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
public class TrejdController {

    // todo
    @Autowired
    TrejdService service;

    @GetMapping("/")
    public String showStartPage() {


        return "home";
    }

    return "home";
  }
    @PostMapping("/")
    public String checkLogin(@RequestParam String email, @RequestParam String password,HttpSession session) {
        User user = service.getUser(email, password);

        if (user != null) {
            System.out.println("test");
            session.setAttribute("user", user);
            return "my-page";
        } else {
            System.out.println("No such user!");
            return "home";
        }
    }
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

        return "home";
    }

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

        if (trejd.getOffer().getUser().getId() == user.getId()
                || trejd.getOrder().getUser().getId() == user.getId()) { //vi kollar om den inloggade användaren är involverad i trejden

            //vi går in i trejden. Vi kollar dens offer, sen kollar vi på offers user, sen kollar vi på users id, sen
            //kollar vi om det är den som är inloggad
            //vi går in i trejden. Vi kollar dens order, sen kollar vi på orderns user, sen kollar vi på users id, sen
            //kollar vi om det är den som är inloggad. vi kollar om den som är inloggad är den som köpt eller den som erbjudit tjänsten

            model.addAttribute("trejd", trejd); //vi lägger in trejden i modellen så att viewn kommer åt den
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

        if (trejd.getOffer().getUser().getId() == user.getId()
                || trejd.getOrder().getUser().getId() == user.getId()) {
            Review review = new Review(trejd.getOffer().getUser(), trejd.getOrder().getUser(), trejd, description, rating);
            service.createReview(review);
            return "tack";
        }

        return "redirect:/";
    }

    @PostMapping("/my-page")
    public String createAnOrder(@RequestParam String location, @RequestParam User user, @RequestParam Skill skill, HttpSession session) {
        OrderTrejd order = new OrderTrejd(location, user, skill);
        service.createOrder(order);
        return "orderlist";

//    }
//    @PostMapping("/my-page")
//   public String uppdateMyInfo(@RequestParam String firstName,@RequestParam String lastName,@RequestParam String password,@RequestParam String email) {
//        //skills???
//        User user = new User(firstName, lastName, password, email);
//        service.createUser(user);
//        return "my-page";
//    }

  
}
