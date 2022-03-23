package com.example.Trejd.Service;

import com.example.Trejd.Repositories.UserRepository;
import com.example.Trejd.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrejdService {

    @Autowired
    UserRepository userRepository;

    public TrejdService(){

    }

    public void saveUser(User user){
    userRepository.save(user);
    }

    public boolean checkPassword(String email, String password) {
        List<User> users = userRepository.findByEmail(email);
        if (users.size() <= 0) {
            return false;
        }

        User user = users.get(0);
        if (user.getPassword().equals(password)) {
            return true;
        } else {
            return false;
        }
    }
}

