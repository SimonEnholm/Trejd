package com.example.Trejd;

import javax.persistence.*;

@Entity
public class OrderTrejd {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String location;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @OneToOne
    @JoinColumn(name="skill")
    private Skill skill;

    public OrderTrejd() {
    }

    public OrderTrejd(String location, User user, Skill skill) {
        this.location = location;
        this.user = user;
        this.skill = skill;
    }

    public Skill getSkill() {
        return skill;
    }


    public User getUser() {
        return user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLocation() { return location; }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setSkill(Skill skill) {
        this.skill = skill;
    }
}
