package com.example.Trejd.Repositories;

import com.example.Trejd.OfferTrejd;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OfferTrejdRepository extends CrudRepository <OfferTrejd, Long> {

    @Query(value = "SELECT * FROM Offer_trejd JOIN Skill ON Offer_trejd.skill = skill.id WHERE Skill_name = ?1", nativeQuery = true)
    List<OfferTrejd> findAllBySkill(String skill);
}
