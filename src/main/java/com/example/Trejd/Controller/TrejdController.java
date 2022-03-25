package com.example.Trejd.Controller;

import com.example.Trejd.Service.TrejdService;
import com.example.Trejd.Skill;
import com.example.Trejd.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;

@Controller
public class TrejdController {

    // todo
    @Autowired
    TrejdService service;

  @GetMapping("/")
  public String getLoginPage() {

    return "login";
  }

  @GetMapping("/orders")
    public String getOrderPage(Model model) {
      model.addAttribute("users", service.findAllUsers());
      return "orderlist";
  }

  @GetMapping("/maketrejd/{id}")

    public String makeTrejdPage(Model model, @PathVariable Long id, RestTemplate restTemplate) {
      //User user = restTemplate.getForObject("http://localhost:8085/maketrejd/" + id, User.class);
      model.addAttribute("user", service.getUserById(id)); // skicka in från url:en
      model.addAttribute("skills", service.getUserSkills(id));
      return "maketrejd";
  }
}
