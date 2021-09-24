package com.example.springdatajpatest.domain.repository;

import com.example.springdatajpatest.domain.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrdersRepository extends JpaRepository<Orders, Long> {
}
