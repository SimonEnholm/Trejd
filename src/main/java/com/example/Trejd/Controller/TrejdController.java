package com.example.Trejd.Controller;

import com.example.Trejd.Service.TrejdService;
import com.example.Trejd.Skill;
import com.example.Trejd.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

    @PostMapping("/test/{name}")
    public String getLoginPage(@RequestParam String email, @PathVariable String name, Model model, HttpSession session) {

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
        return  "home";
    }
    @GetMapping("/login")
    public String getLoginPage2() {
        return "login";
    }
    @PostMapping("/login")
    public String checkLogin(@RequestParam String email, @RequestParam String password,HttpSession session) {
        User user = service.getUser(email,password);

      if(user!=null){
          session.setAttribute("user",user);
          return "my-page";
      }
      else{
          System.out.println("No such user!");
          return "login";
      }
    }
    @GetMapping("/my-page")
    public String getMyPage() {
        return "my-page";
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
    public String createUserPage(@ModelAttribute User user, Model model){
        System.out.println(user.getEmail());
         if(!service.saveUser(user)){
          System.out.println("User already exist!");
          return "create-user";
      }
      return "my-page";
  }
}
