package com.example.Trejd;

import javax.persistence.*;
import java.util.List;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany (mappedBy = "user", cascade = CascadeType.ALL)
    private List<UserSkills> userSkills;
    @Transient
    private Long skillId1;
    @Transient
    private Long skillId2;
    @Transient
    private Long skillId3;

    private String firstName;
    private String lastName;
    private String email;
    private String password;
    //private boolean variation
    private double balance;
    private double rating;
    private double latitude;
    private double longitude;
    private String location;
    private String image;


    public User() {
    }

    public User(String firstName, String lastName, String password, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }


    public void setUser(String firstName, String lastName, String email, String password){
     this.firstName = firstName;
     this.lastName = lastName;
     this.email = email;
     this.password = password;
 }
    public Long getSkillId1() {
        return skillId1;
    }
    public void setSkillId1(Long skillId1) {
        this.skillId1 = skillId1;
    }

    public Long getSkillId2() {
        return skillId2;
    }

    public void setSkillId2(Long skillId2) {
        this.skillId2 = skillId2;
    }

    public Long getSkillId3() {
        return skillId3;
    }

    public void setSkillId3(Long skillId3) {
        this.skillId3 = skillId3;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public List<UserSkills> getUserSkills() {
        return userSkills;
    }

    public void setUserSkills(List<UserSkills> userSkills) {
        this.userSkills = userSkills;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
    public void addToBalance(double workHours){
        balance += workHours;
    }
    public void subtractFromBalance(double workHours){
        balance -= workHours;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
