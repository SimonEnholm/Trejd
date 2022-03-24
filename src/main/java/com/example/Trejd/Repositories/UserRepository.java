package com.example.Trejd.Repositories;

import com.example.Trejd.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface UserRepository extends CrudRepository <User, Long> {

    @Query(value = "SELECT * FROM USER WHERE email=?1 AND password=?2",nativeQuery = true)
   User getUserByEmailAndPassword(String email, String password);

    List<User> findByEmail(String email);

}

