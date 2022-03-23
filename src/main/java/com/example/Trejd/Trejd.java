package com.example.Trejd;

import javax.persistence.*;

@Entity
public class Trejd {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    @JoinColumn(name= "offer_id")
    private OfferTrejd offerTrejd;

    @OneToOne
    @JoinColumn(name = "order_id")
    private OrderTrejd orderTrejd;

    //todo tidslåsning, deposition?
    private boolean completed;

    public Trejd(){
    }

    /*public Trejd(OfferTrejd offer, OrderTrejd order, boolean completed) {
        this.offerTrejd = offer;
        this.orderTrejd = order;
        this.completed = completed;
    }*/

    public void setOfferTrejd(OfferTrejd offerTrejd) {
        this.offerTrejd = offerTrejd;
    }

    public void setOrderTrejd(OrderTrejd orderTrejd) {
        this.orderTrejd = orderTrejd;
    }

    public OrderTrejd getOrder() {
        return orderTrejd;
    }

    public OfferTrejd getOffer() {
        return offerTrejd;
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
