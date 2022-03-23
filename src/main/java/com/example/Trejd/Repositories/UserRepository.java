package com.example.Trejd.Repositories;

import com.example.Trejd.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository <User, Long> {

    User findByEmail(String email);
}
