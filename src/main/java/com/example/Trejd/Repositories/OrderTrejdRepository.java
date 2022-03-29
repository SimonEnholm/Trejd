package com.example.Trejd.Repositories;

import com.example.Trejd.OrderTrejd;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OrderTrejdRepository extends CrudRepository <OrderTrejd, Long> {

    @Query(value = "SELECT * FROM skill WHERE skill_name = '?'", nativeQuery = true)
    List<OrderTrejd> findByName(String name);

    @Query(value = "SELECT user.first_name, user.last_name, order_trejd.location, skill.skill_name FROM order_trejd\n" +
            " JOIN user ON user.id = order_trejd.user_id\n" +
            " JOIN user_skills ON user_skills.user_id = user.id\n" +
            " JOIN skill ON skill.id = user_skills.skill_id\n" +
            " WHERE location = '?1' AND skill_name = '?2'", nativeQuery = true)
    List<OrderTrejd> findByLocation(String location, String skill);

}
