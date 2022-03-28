package com.example.Trejd.Repositories;

import com.example.Trejd.OfferTrejd;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OfferTrejdRepository extends CrudRepository <OfferTrejd, Long> {

    // sökmetod via text
    @Query(value = "SELECT * FROM Offer_trejd JOIN Skill ON Offer_trejd.skill = skill.id WHERE Skill_name = ?1", nativeQuery = true)
    List<OfferTrejd> findAllBySkill(String skill);


    //viewOfferList.html -  detta tar fram listan av skapade offers!
    @Query(value = "SELECT * FROM offer_trejd\n" +
            "JOIN user ON user.id = offer_trejd.user_id\n" +
            "JOIN skill on skill.id = offer_trejd.skill", nativeQuery = true)
    List<OfferTrejd> getOffersJoinUserJoinSkill();
}
