package com.example.Trejd;

import javax.persistence.*;

@Entity
public class UserSkills {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "skill_id")
    private Skill skill;

    public Skill getSkill() {
        return skill;
    }

    public User getUser() {
        return user;
    }

}
