package com.example.Trejd;

import com.example.Trejd.Service.TrejdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Starter implements CommandLineRunner {

    @Autowired
    TrejdService service;

    public Starter(){

    }
    public void run(String... args) {
//        User user = new User();
//        user.setUser("Admin firstname", "Admin lastname", "admin@email", "xxxx");
//        service.saveUser(user);
//        System.out.println(user.getFirstName());

        String email = "hej@hej.se";
        String password = "lösen123";

        boolean asd = service.checkPassword(email, password);
    }

}
