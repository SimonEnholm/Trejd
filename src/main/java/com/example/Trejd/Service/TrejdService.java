package com.example.Trejd.Service;

import com.example.Trejd.*;
import com.example.Trejd.Repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrejdService {
    @Autowired
    UserSkillsRepository userSkillsRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    SkillRepository skillRepository;

    @Autowired
    OrderRepository orderRepository;

    public TrejdService(){

    }
public void saveUser(User user){
    userRepository.save(user);
    }

public List<Skill> getAllByCategoryId(Long id){
        return (List<Skill>) skillRepository.findAllByCategoryId(id);
    }
    public List<String> getAllCategoriesByUser(Long id){
        return (List<String>) userSkillsRepository.getAllCategoryNamesByUserId(id);
    }

    public List <OrderTrejd> getAllOrders(){
        return (List<OrderTrejd>) orderRepository.findAll();
    }

    public List<Skill> getAllSkillsByCategoryId(Long id){
        return skillRepository.getAllSkillsByCategoryId(id);
    }

    }



