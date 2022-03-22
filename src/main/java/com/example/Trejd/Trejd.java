package com.example.Trejd;

import javax.persistence.*;

@Entity
public class Trejd {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name= "offer_id")
    private Offer offer;

    @OneToOne
    @JoinColumn(name = "order_id")
    private Order order;

    //todo tidslåsning, deposition?
    private boolean completed;

    public Order getOrder() {
        return order;
    }

    public Offer getOffer() {
        return offer;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}
