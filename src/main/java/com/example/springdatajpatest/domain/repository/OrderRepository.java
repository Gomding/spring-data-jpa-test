package com.example.springdatajpatest.domain.repository;

import com.example.springdatajpatest.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
