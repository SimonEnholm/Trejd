package com.example.Trejd;

import com.example.Trejd.Service.TrejdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
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
        service.saveUser(user);
        System.out.println(user.getFirstName());

        List<Skill> s = service.getAllByCategoryId(1l);

        for(Skill sk : s) {
            System.out.println(sk.getSkillName());
        }

        List<String> sko = service.getAllCategoriesByUser(1l);
        for(String p : sko){
            System.out.println(p);
        }


        List<OrderTrejd> l = service.getAllOrders();

        for(OrderTrejd or : l) {
            System.out.println(or.getLocation());
        }


        List<Skill> CategoryList = service.getAllSkillsByCategoryId(1l);
        for(Skill k : CategoryList){
            System.out.println(k.getSkillName());
        }


    }
}


