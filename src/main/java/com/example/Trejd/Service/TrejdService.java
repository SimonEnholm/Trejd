package com.example.Trejd.Service;

import com.example.Trejd.Repositories.UserRepository;
import com.example.Trejd.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TrejdService {

    @Autowired
    UserRepository userRepository;

    public TrejdService(){

    }
public void saveUser(User user){
    userRepository.save(user);
    }
}
