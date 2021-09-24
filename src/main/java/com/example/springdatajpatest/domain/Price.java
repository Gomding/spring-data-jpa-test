package com.example.springdatajpatest.domain;

import javax.persistence.*;

@Entity
public class Price {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long value;

    public Price() {
    }

    public Price(Long value) {
        this(null, value);
    }

    public Price(Long id, Long value) {
        this.id = id;
        this.value = value;
    }

    public Long getId() {
        return id;
    }

    public Long getValue() {
        return value;
    }
}