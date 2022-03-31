package com.example.Trejd.Controller;


import com.example.Trejd.*;
import com.example.Trejd.Service.TrejdService;

import com.example.Trejd.Service.TrejdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.ValidationUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;


@Controller
public class TrejdController {

    // todo
    @Autowired
    TrejdService service;

    @GetMapping("/")
    public String showStartPage(Model model) {
        User user = new User();
        model.addAttribute("user",user);
        return "home";
    }
    @PostMapping("/")
    public String checkLogin(@ModelAttribute User user, HttpSession session, BindingResult result) {

        ValidationUtils.rejectIfEmpty(result,"email","Email cant be empty");
        ValidationUtils.rejectIfEmpty(result,"password","Password cant be empty");

        if(result.hasErrors()){
            return "home";
        }
        User loggedInUser = service.getUser(user.getEmail(),user.getPassword());
        if (loggedInUser != null) {
            session.setAttribute("user", loggedInUser);
            return "my-page";
        } else {
            //TODO: Print on page.
            System.out.println("No such user!");
            return "home";
        }
    }

    @GetMapping("/offerlist")
    public String getOfferPage(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        List<Skill> skills = service.getAllSkills();
        model.addAttribute("skills",skills);
        model.addAttribute("users", service.findAllOrdersSorted(user));

        return "viewOfferList";
    }

    @PostMapping("/offerlist")
    public String getAllOrdersFiltered(@RequestParam Long skillId,HttpSession session,Model model) {
        List<Skill> skills = service.getAllSkills();
        model.addAttribute("skills",skills);
        User user = (User) session.getAttribute("user");
        model.addAttribute("isFiltered",true);
        model.addAttribute("skillId",skillId);
        List<OrderTrejd> orders = service.findAllOrdersSortedAndFiltered(user, skillId);
        for(OrderTrejd o : orders){
            System.out.println(o.getLocation());
            System.out.println(o.getDescription());

        }
        model.addAttribute("wishes", orders);
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
  @GetMapping("/maketrejd/{id}/{skillId}")
    public String makeTrejdPage(Model model, @PathVariable Long id, @PathVariable Long skillId) {
      User user = service.getUserById(id);
      //List<Review> reviews = service.getReviewsOnUser(user);
      model.addAttribute("user", user);
      //model.addAttribute("reviews", reviews);
      Skill skill = service.getSkillById(skillId);
      model.addAttribute("selectedSkill",skill);
//    public String trejdProfilePage(Model model, @PathVariable Long id) {
//      model.addAttribute("user", service.getUserById(id)); // skicka in från url:en

      model.addAttribute("skills", service.getUserSkills(id));
      return "maketrejd";
  }




//    @PostMapping("/")
//    public String checkLogin(@RequestParam String email, @RequestParam String password,HttpSession session) {
//        User user = service.getUser(email, password);

//  @PostMapping("/maketrejd/{id}")
//  public String createTrejd(HttpSession session, @PathVariable User performerId, Model model){
//      User user = (User) session.getAttribute("user");
//      model.addAttribute("order", service.createTrejd(user, performerId));

//
//      return "tack";
//  }



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
        session.setAttribute("user",user);
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
////        User user = new User(firstName, lastName, password, email);
//        service.createUser(user);
//        return "my-page";
//    }

    //todo sökfunktion
    @GetMapping("/orders")
    public String getOrderPage(Model model,@RequestParam(defaultValue = "distance") String sortedBy,HttpSession session) {
        User user = (User) session.getAttribute("user");
        List<Skill> skills = service.getAllSkills();
        model.addAttribute("skills",skills);
        if(sortedBy.equals("distance"))
            model.addAttribute("users", service.findAllUsersSorted(user,true));
        else{
            model.addAttribute("users", service.findAllUsersSorted(user,false));
        }
        return "viewPerformers";
    }
    @PostMapping ("/orders")
    public String getAllPerformersFiltered(@RequestParam Long skillId,HttpSession session,Model model){
        List<Skill> skills = service.getAllSkills();
        model.addAttribute("skills",skills);
        User user = (User) session.getAttribute("user");
        model.addAttribute("isFiltered",true);
        model.addAttribute("skillId",skillId);
        model.addAttribute("performers", service.findAllUsersSortedAndFiltered(user,true,skillId));
        return "viewPerformers";
    }

    @GetMapping("/create-user")
    public String viewUserPage(Model model){
        User user = new User();
        Map<String,List<Skill>> skillsAndCat = service.getAllSkillsAndCategories();
        model.addAttribute("skills",skillsAndCat);
        model.addAttribute("user",user);
        return "create-user";
    }

    @PostMapping("/create-user")
    public String createUserPage(@ModelAttribute User user, Model model,HttpSession session,BindingResult result){
        //Validation
        ValidationUtils.rejectIfEmpty(result,"firstName","First name cant be empty");
        ValidationUtils.rejectIfEmpty(result,"lastName","Last name cant be empty");
        ValidationUtils.rejectIfEmpty(result,"email","Email cant be empty");
        ValidationUtils.rejectIfEmpty(result,"password","Password cant be empty");
        ValidationUtils.rejectIfEmpty(result,"location","Location cant be empty");
        ValidationUtils.rejectIfEmpty(result,"skillId1","Skill cant be empty");

        if(result.hasErrors()){
            return "redirect:/create-user";
        }
        if(!service.saveUser(user)){
            System.out.println("User already exist!");
            return "create-user";
        }

        Skill skill = service.getSkillById(user.getSkillId1());
        Skill skill2 = service.getSkillById(user.getSkillId2());
        Skill skill3 = service.getSkillById(user.getSkillId3());
        UserSkills us = new UserSkills();
        us.setSkill(skill);
        us.setUser(user);
        service.saveUserSkill(us);
        UserSkills us2 = new UserSkills();
        UserSkills us3 = new UserSkills();


        if(user.getSkillId2()!=null) {
            us2.setSkill(skill2);
            us2.setUser(user);
            service.saveUserSkill(us2);
        }
        if(user.getSkillId2()!=null) {
            us3.setSkill(skill3);
            us3.setUser(user);
            service.saveUserSkill(us3);
        }
        session.setAttribute("user",user);

        return "my-page";
    }

    // todo få med skillen vi valde. bara den ska synas
    @GetMapping({"/create-order", "/create-order/{performerId}/{skillId}"})
    public String createOrder(@PathVariable(required = false) Long performerId,
                              @PathVariable (required = false) Long skillId, HttpSession session, Model model){
        System.out.println("NU");
        OrderTrejd order = new OrderTrejd();

        Map<String,List<Skill>> skillsAndCat = service.getAllSkillsAndCategories();
        model.addAttribute("skills",skillsAndCat);

        if(performerId != null){
            System.out.println("Performer not null");
            if(skillId!=null) {
                System.out.println("skill id not null");
                order.setSkill(service.getSkillById(skillId));
                model.addAttribute("performer",service.getUserById(performerId));
                model.addAttribute("order", order);
            }
            return "create-order";
        }
        model.addAttribute("order", order);
        return "create-order";
    }

    // todo länkas vart?
    @PostMapping({"/create-order","/create-order/{performerId}/{skillId}"})
    public String saveOrder(@ModelAttribute OrderTrejd order, HttpSession session,
                            @PathVariable(required = false) Long performerId, @PathVariable (required = false) Long skillId) {
        //Nice to have: If performer exists sen mail to performer and set order to pending.

        User user = (User) session.getAttribute("user");
        System.out.println(user.getFirstName());
        order.setUser(user);
        Skill skill = service.getSkillById(order.getSkillId());
        order.setSkill(skill);
        Trejd trejd = new Trejd();
        trejd.setOrderTrejd(order);
        trejd.setCompleted(false);
        if(performerId!=null){
            trejd.setPerformer(service.getUserById(performerId));
        }
        service.saveOrder(order);
        service.saveTrejd(trejd);
        return "order-confirm";
    }

    @GetMapping("/updateuserinfo")
    public String showUserInfo (HttpSession session, Model model){
        User user = (User)session.getAttribute("user");
        model.addAttribute("user",user);
        return "updateuserinfo";
    }
    @PostMapping("/updateuserinfo")
    public String updateUserInfo (@ModelAttribute User user, HttpSession session){
        //service.updateUser(user.getFirstName(),user.getLastName(),user.getEmail(),user.getPassword(), user.getId());
        service.saveUser(user);
        session.setAttribute("user",user);
        return "my-page";
    }


}
