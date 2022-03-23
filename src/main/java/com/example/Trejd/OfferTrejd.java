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
    private String location;


    public OfferTrejd(){

    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {return user; }

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
}
