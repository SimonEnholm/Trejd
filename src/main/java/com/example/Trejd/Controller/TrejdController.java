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

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "home";
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

    // todo fetmarkering p?? skillen vi valde.
    // todo Review utkommenterat???
  @GetMapping("/maketrejd/{id}/{skillId}")
    public String makeTrejdPage(Model model, @PathVariable Long id, @PathVariable Long skillId) {
      User user = service.getUserById(id);
      //List<Review> reviews = service.getReviewsOnUser(user);
      model.addAttribute("user", user);
      //model.addAttribute("reviews", reviews);
      Skill skill = service.getSkillById(skillId);
      model.addAttribute("selectedSkill",skill);
//    public String trejdProfilePage(Model model, @PathVariable Long id) {
//      model.addAttribute("user", service.getUserById(id)); // skicka in fr??n url:en

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

        //Inv??rden fr??n anv??ndare
        // @RequestParam /test?email="twana@test.se" <----
        // @PathVariable /test/Twana ex. pagination eller ett id

        //Model f??ljer bara med till HTML-sidan
        //Model -> Spara till view (HTML/Thymeleaf)
        //model.addAttribute(user);
        //model.getAttribute(user);

        // Sessions f??ljer med under medans anv??ndaren ??r inloggad
        //session.getAttribute(user) -> Vid login. setSession;
        //session.setAttribute(user);

//        return "home";
//    }

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
    public String getMyPage(HttpSession session, Model model) {
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

    //Anropas n??r b??da har avslutat trejden. N??r utf??raren ??r klar och kund godk??nt s?? skickas man till reviewn.

   @GetMapping("/trejdInfo/{id}") //info om specifikt trejd-id
    public String getTrejdInfo(@PathVariable Long id, Model model, HttpSession session) {
        Trejd trejd = service.getTrejd(id); //vi plockar ut hela trejden fr??n databasen baserat p?? id
        User user = (User) session.getAttribute("user"); //vi plockar ut den inloggade anv??ndaren. getAttribute returnerar ett objekt. Den vet inte vad det ??r f??r typ, d??rf??r ber??ttar vi det f??r den med (User). Man "castar".
        if (user == null) { // dubbelkollar att n??gon ??r inloggad, ??r man inte det s?? redirectas man till loginsidan.
            return "redirect:/login";
        }

        if (trejd.getPerformer().getId() == user.getId()
                || trejd.getOrder().getUser().getId() == user.getId()) { //vi kollar om den inloggade anv??ndaren ??r involverad i trejden
            boolean writtenByPerformer = trejd.getPerformer().getId() == user.getId();
            //vi g??r in i trejden. Vi kollar dens offer, sen kollar vi p?? offers user, sen kollar vi p?? users id, sen
            //kollar vi om det ??r den som ??r inloggad
            //vi g??r in i trejden. Vi kollar dens order, sen kollar vi p?? orderns user, sen kollar vi p?? users id, sen
            //kollar vi om det ??r den som ??r inloggad. vi kollar om den som ??r inloggad ??r den som k??pt eller den som erbjudit tj??nsten

            model.addAttribute("trejd", trejd); //vi l??gger in trejden i modellen s?? att viewn kommer ??t den
            model.addAttribute("writtenByPerformer", writtenByPerformer);
            return "trejdInfo"; //vi kommer in p?? trejdInfo-sidan
        } else {
            return "redirect:/";//har personen inte tillg??ng till denna sida redirectas den till startsidan
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
            service.transferTime(trejd.getPerformer(), user,trejd.getOrder().getEstimatedTime());
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

    //todo s??kfunktion
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
        ValidationUtils.rejectIfEmpty(result,"image","Image cant be empty");
        System.out.println(user.getImage());
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

    // todo f?? med skillen vi valde. bara den ska synas
    @GetMapping({"/create-order", "/create-order/{performerId}/{skillId}"})
    public String createOrder(@PathVariable(required = false) Long performerId,
                              @PathVariable (required = false) Long skillId, HttpSession session, Model model){
        System.out.println("NU");
        OrderTrejd order = new OrderTrejd();
        User user = (User) session.getAttribute("user");
        order.setUser(user);

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

    // todo l??nkas vart?
    @PostMapping({"/create-order","/create-order/{performerId}/{skillId}"})
    public String saveOrder(@ModelAttribute OrderTrejd order, HttpSession session,
                            @PathVariable(required = false) Long performerId, @PathVariable (required = false) Long skillId, Model model) {
        //Nice to have: If performer exists sen mail to performer and set order to pending.
        String goTo = "order-confirm";
        User user = (User) session.getAttribute("user");
        System.out.println(user.getFirstName());
        order.setUser(user);
        Skill skill = service.getSkillById(order.getSkillId());
        order.setSkill(skill);
        Trejd trejd = new Trejd();
        trejd.setOrderTrejd(order);
        trejd.setCompleted(true); // ska egebtligen s??ttas till true n??r den ??r bekr??ftad
        if(performerId!=null){
            trejd.setPerformer(service.getUserById(performerId));
            model.addAttribute("trejd", trejd);
            goTo = "trejd-confirm";
        }
        service.saveOrder(order);
        service.saveTrejd(trejd);
        return goTo;
    }

    @GetMapping("/trejd-confirm")
    public String trejdConfirm(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        Trejd trejd = service.getLastTrejd();
        model.addAttribute("trejd", trejd);
        return "trejd-confirm";
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
