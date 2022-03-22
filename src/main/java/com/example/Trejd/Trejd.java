package com.example.Trejd;

import javax.persistence.*;

@Entity
public class Trejd {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name= "offer_id")
    private OfferTrejd offer;

    @OneToOne
    @JoinColumn(name = "order_id")
    private OrderTrejd order;

    //todo tidslåsning, deposition?
    private boolean completed;

    public OrderTrejd getOrder() {
        return order;
    }

    public OfferTrejd getOffer() {
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
