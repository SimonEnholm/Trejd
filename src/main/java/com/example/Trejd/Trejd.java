package com.example.Trejd;

import javax.persistence.*;

@Entity
public class Trejd {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    @JoinColumn(name= "performer_id")
    private User performer;

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

    public void setPerformer(User performer) {
        this.performer = performer;
    }

    public void setOrderTrejd(OrderTrejd orderTrejd) {
        this.orderTrejd = orderTrejd;
    }

    public OrderTrejd getOrder() {
        return orderTrejd;
    }

    public User getPerformer() {
        return performer;
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
