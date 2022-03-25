package com.example.Trejd.Controller;

import com.example.Trejd.OfferTrejd;
import com.example.Trejd.OrderTrejd;
import com.example.Trejd.Service.TrejdService;
import com.example.Trejd.Skill;
import com.example.Trejd.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
//        return  "home";
//    }



    @GetMapping("/my-page")
    public String getMyPage() {
      //User user = (User)session.getAttribute("user");
      // if no user restrict view.
        return "my-page";
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


}
