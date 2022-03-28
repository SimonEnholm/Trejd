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
    @Transient
    private Long skillId;
    @Lob
    private String description;
    private double longitude;
    private double latitude;

    public OrderTrejd() {
    }

    public OrderTrejd(String location, User user, Skill skill) {
        this.location = location;
        this.user = user;
        this.skill = skill;
    }

    public Long getSkillId() {
        return skillId;
    }

    public void setSkillId(Long skillId) {
        this.skillId = skillId;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
