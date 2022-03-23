package com.example.Trejd.Repositories;

import com.example.Trejd.Skill;
import com.example.Trejd.UserSkills;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserSkillsRepository extends CrudRepository <UserSkills, Long> {


    @Query(value = "select skill_name from user_skills join skill\n" +
            "on skill.id=skill_id\n" +
            "Where user_id=?1",nativeQuery = true)
           List<String> getAllCategoryNamesByUserId(Long id);

}
