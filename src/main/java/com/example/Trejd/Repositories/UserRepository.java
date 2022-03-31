package com.example.Trejd.Repositories;

import com.example.Trejd.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface UserRepository extends CrudRepository <User, Long> {


    @Query(value = "SELECT * FROM USER WHERE email=?1 AND password=?2",nativeQuery = true)
   User getUserByEmailAndPassword(String email, String password);

    List<User> findByEmail(String email);

    @Query(value = "SELECT * FROM user WHERE user.id != ?1", nativeQuery = true)
    List<User> findAllByQuery(Long id);

    @Query(value = "SELECT * FROM user\n" +
            "JOIN user_skills\n" +
            "ON user.id=user_skills.user_id\n" +
            "WHERE skill_id = ?1\n" +
            "AND user.id != ?2",nativeQuery = true)
    List<User> findAllBySkillId(Long skillId, Long id);

   @Query(value = "UPDATE user SET firstName =?1, lastName =?2, email=?3, password=?4 Where Id=?5", nativeQuery = true)
    User updateUser(String firstName, String lastName, String email, String password, Long Id);
}

