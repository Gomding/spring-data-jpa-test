package com.example.springdatajpatest.domain;

import javax.persistence.*;

@Entity
public class Orders {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String productName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Account user;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "price_ID")
    private Price price;

    public Orders() {
    }

    public Orders(String product) {
        this(null, product);
    }

    public Orders(Long id, String productName) {
        this.id = id;
        this.productName = productName;
    }

    public Orders byUser(Account user) {
        this.user = user;
        return this;
    }

    public Orders setPrice(Price price) {
        this.price = price;
        return this;
    }

    public Long getId() {
        return id;
    }

    public String getProductName() {
        return productName;
    }

    public Account getUser() {
        return user;
    }

    public Price getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", productName='" + productName + '\'' +
                ", user=" + user.getAccountName() +
                '}';
    }
}
