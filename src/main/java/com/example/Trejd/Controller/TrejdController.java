package com.example.Trejd.Controller;


import com.example.Trejd.*;
import com.example.Trejd.Service.TrejdService;

import com.example.Trejd.Service.TrejdService;
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

    @GetMapping("/offerlist")
    public String getOfferPage(Model model) {
        model.addAttribute("users", service.getOffersJoinUserJoinSkill());
        return "viewOfferList";
    }

//  @GetMapping("/performers")
//    public String getOrderPage(Model model, HttpSession session) {
//      User user = (User) session.getAttribute("user");
//      model.addAttribute("users", service.findAllUsers(user.getId()));
//
//      return "viewPerformers";
//  }

    // todo fetmarkering på skillen vi valde.
  @GetMapping("/maketrejd/{id}")

    public String trejdProfilePage(Model model, @PathVariable Long id) {
      model.addAttribute("user", service.getUserById(id)); // skicka in från url:en
      model.addAttribute("skills", service.getUserSkills(id));
      return "maketrejd";
  }

//  @PostMapping("/maketrejd/{id}")
//  public String createTrejd(HttpSession session, @PathVariable User performerId, Model model){
//      User user = (User) session.getAttribute("user");
//      model.addAttribute("order", service.createTrejd(user, performerId));
//
//      return "tack";
//  }

    @PostMapping("/")
    public String checkLogin(@RequestParam String email, @RequestParam String password,HttpSession session) {
        System.out.println("TeSTARRR");
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

//        return "home";
//    }

    @GetMapping("/login")
    public String getLoginPage2() {
        return "login";
    }

//    @PostMapping("/login")
//    public String checkLogin(@RequestParam String email, @RequestParam String password, HttpSession session) {
//        User user = service.getUser(email, password);
//
//        if (user != null) {
//            session.setAttribute("user", user);
//            return "my-page";
//        } else {
//            System.out.println("No such user!");
//            return "login";
//        }
//    }

//        return  "home";
//    }




    @GetMapping("/my-page")
    public String getMyPage(HttpSession session) {
      User user = (User)session.getAttribute("user");
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

//    @PostMapping("/my-page")
//    public String createAnOrder(@RequestParam String location, @RequestParam User user, @RequestParam Skill skill, HttpSession session) {
//        OrderTrejd order = new OrderTrejd(location, user, skill);
//        service.createOrder(order);
//        return "orderlist";
//
//    }
//    @PostMapping("/my-page")
//   public String updateMyInfo(@RequestParam String firstName,@RequestParam String lastName,@RequestParam String password,@RequestParam String email) {
//        //skills???
//        User user = new User(firstName, lastName, password, email);
//        service.createUser(user);
//        return "my-page";
//    }

    //todo sökfunktion
    @GetMapping("/orders")
    public String getOrderPage(Model model,@RequestParam(defaultValue = "distance") String sortedBy,HttpSession session) {
        User user = (User) session.getAttribute("user");
        if(sortedBy.equals("distance"))
            model.addAttribute("users", service.findAllUsersSorted(user,true));
        else{
            model.addAttribute("users", service.findAllUsersSorted(user,false));
        }
        return "viewPerformers";
    }

    @GetMapping("/create-user")
    public String viewUserPage(Model model){
        User user = new User();
        List<Skill> skills = service.getAllSkills();
        model.addAttribute("skills",skills);
        model.addAttribute("user",user);
        return "create-user";
    }

    @PostMapping("/create-user")
    public String createUserPage(@ModelAttribute User user, Model model,HttpSession session){

        if(!service.saveUser(user)){
            System.out.println("User already exist!");
            return "create-user";
        }
        Skill skill = service.getSkillById(user.getSkillId());
        UserSkills us = new UserSkills();
        us.setSkill(skill);
        us.setUser(user);
        service.saveUserSkill(us);
        session.setAttribute("user",user);
        return "my-page";
    }

    // todo få med skillen vi valde. bara den ska synas
    @GetMapping({"/create-order", "/create-order/{performerId}"})
    public String createOrder(@PathVariable(required = false) Long performerId, HttpSession session, Model model){
        OrderTrejd order = new OrderTrejd();
        //User user = (User) session.getAttribute("user");

//        if(order.getSkillId()==0){
//            return "create-order";
//        }
        List<Skill> skills = service.getAllSkills();
        model.addAttribute("skills", skills);
        model.addAttribute("order", order);

        if(performerId == null){
            System.out.println("perfomer ID null");
            return "create-order";
        }
        model.addAttribute("performer", service.getUserById(performerId)); // skicka in från url:en
        return "create-order";
    }

    // todo länkas vart?
    @PostMapping("/create-order")
    public String saveOrder(@ModelAttribute OrderTrejd order, HttpSession session) {
        order.setUser( (User) session.getAttribute("user"));
        Skill skill = service.getSkillById(order.getSkillId());
        order.setSkill(skill);
        service.saveOrder(order);
        return "viewOfferList";
    }







}
