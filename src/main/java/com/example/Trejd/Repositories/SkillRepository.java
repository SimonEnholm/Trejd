package com.example.Trejd.Repositories;

import com.example.Trejd.Category;
import com.example.Trejd.Skill;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SkillRepository extends CrudRepository <Skill, Long> {

    List<Skill> findAllByCategoryId(Long id);

    @Query(value = "SELECT * FROM skill where category_id = ?", nativeQuery = true)
    List<Skill>getAllSkillsByCategoryId(Long id);

}
