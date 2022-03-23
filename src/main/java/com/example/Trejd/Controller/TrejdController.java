package com.example.Trejd.Controller;

import com.example.Trejd.Service.TrejdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TrejdController {

    // todo
    @Autowired
    TrejdService service;

  @GetMapping("/")
  public String getLoginPage() {

    return "login";
  }
}
