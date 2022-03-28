package com.example.Trejd;

import javax.persistence.*;

@Entity
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "customer_id")
    private User customer;

    @OneToOne
    @JoinColumn(name = "performer_id")
    private User performer;

    @OneToOne
    @JoinColumn(name = "trejd_id")
    private Trejd trejd;

    private String description;
    private int rating;
    private boolean writtenByPerformer;



    public Review(){
    }
    public Review (User customer, User performer, Trejd trejd, String description, int rating, boolean writtenByPerformer){
        this.customer=customer;
        this.performer=performer;
        this.trejd=trejd;
        this.description=description;
        this.rating=rating;
        this.writtenByPerformer=writtenByPerformer;

    }
    public Trejd getTrejd() {
        return trejd;
    }

    public User getPerformer() {
        return performer;
    }

    public User getCustomer() {
        return customer;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
