package com.example.Trejd.Repositories;

import com.example.Trejd.Skill;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SkillRepository extends CrudRepository <Skill, Long> {

    List<Skill> findAllByCategoryId(Long id);

    @Query(value = "SELECT * FROM skill where category_id = ?", nativeQuery = true)
    List<Skill>getAllSkillsByCategoryId(Long id);

    @Query(value = "SELECT * FROM skill\n" +
            " JOIN user_skills ON user_skills.skill_id = skill.id\n" +
            " JOIN user ON user.id = user_skills.user_id\n" +
            " WHERE user.id = ?1", nativeQuery = true)
    List<Skill> getSkillsByUserId(Long id);
}
