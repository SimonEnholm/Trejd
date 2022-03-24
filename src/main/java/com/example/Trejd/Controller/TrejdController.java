package com.example.Trejd.Controller;

import com.example.Trejd.Service.TrejdService;
import com.example.Trejd.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
}
