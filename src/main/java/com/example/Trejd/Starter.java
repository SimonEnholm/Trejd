package com.example.Trejd;

import com.example.Trejd.Service.TrejdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Starter implements CommandLineRunner {

    @Autowired
    TrejdService service;

    public Starter(){

    }
    public void run(String... args) {
        User user = new User();
        user.setUser("Admin firstname", "Admin lastname","admin@email","xxxx");
        if(service.saveUser(user)==true){
            System.out.println("User created");
        }
        else {
            System.out.println("User Email already exists");
        }
        List<Review> testingGetting = service.getAllReviewsByCustomerId(1L);

        for(int i = 0; i < testingGetting.size(); i++){
            System.out.println(testingGetting.get(i).getDescription());
        }
    }
}
