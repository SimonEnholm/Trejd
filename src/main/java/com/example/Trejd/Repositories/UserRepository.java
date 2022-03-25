package com.example.Trejd.Repositories;

import com.example.Trejd.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface UserRepository extends CrudRepository <User, Long> {

    List<User> findByEmail(String email);

}

