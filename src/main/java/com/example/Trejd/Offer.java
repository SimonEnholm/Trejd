package com.example.Trejd;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Offer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //private Long userId;
    //private geo location;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
