package com.example.Trejd;

import javax.persistence.*;

@Entity
public class OfferTrejd {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @OneToOne
    @JoinColumn(name = "skill")
    private Skill skill;
    private String location;
    private String description;



    public OfferTrejd(){

    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {return user; }

    public OfferTrejd() {
    }

    public OfferTrejd(User user, Skill skill, String location, String description) {
        this.user = user;
        this.skill = skill;
        this.location = location;
        this.description = description;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getLocation() { return location; }

    public void setLocation(String location){
        this.location = location;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Skill getSkill() {
        return skill;
    }

    public void setSkill(Skill skill) {
        this.skill = skill;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
