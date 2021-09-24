package com.example.springdatajpatest.domain;

import javax.persistence.*;

@Entity
@Table(name = "orders")
public class Order {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Account user;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "price_ID")
    private Price price;

    public Order() {
    }

    public Order(String product) {
        this(null, product);
    }

    public Order(Long id, String product) {
        this.id = id;
        this.product = product;
    }

    public Order byUser(Account user) {
        this.user = user;
        return this;
    }

    public Order setPrice(Price price) {
        this.price = price;
        return this;
    }

    public Long getId() {
        return id;
    }

    public String getProduct() {
        return product;
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
                ", product='" + product + '\'' +
                ", user=" + user.getAccountName() +
                '}';
    }
}
