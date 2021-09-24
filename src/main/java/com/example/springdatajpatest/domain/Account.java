package com.example.springdatajpatest.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Account {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String accountName;
    private String name;
    private int age;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Orders> orders = new ArrayList<>();

    public Account() {
    }

    public Account(String accountName, String name, int age) {
        this(null, accountName, name, age);
    }

    public Account(Long id, String accountName, String name, int age) {
        this.id = id;
        this.accountName = accountName;
        this.name = name;
        this.age = age;
    }

    public Long getId() {
        return id;
    }

    public String getAccountName() {
        return accountName;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public List<Orders> getOrder() {
        return orders;
    }

    public Account addOrder(Orders orders) {
        this.orders.add(orders);
        orders.byUser(this);
        return this;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", accountName='" + accountName + '\'' +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", order=" + orders +
                '}';
    }
}
