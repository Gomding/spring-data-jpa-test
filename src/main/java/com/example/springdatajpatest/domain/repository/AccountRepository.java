package com.example.springdatajpatest.domain.repository;

import com.example.springdatajpatest.domain.Account;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Set;

public interface AccountRepository extends JpaRepository<Account, Long> {

    @Query("select a from Account a join fetch a.orders")
    List<Account> findAllJoinFetch();

    @Query("select a from Account a join fetch a.orders")
    Set<Account> findAllSetJoinFetch();

    @Query("select distinct a from Account a join fetch a.orders")
    List<Account> findAllDistinctJoinFetch();

    @EntityGraph(attributePaths = {"orders", "orders.user"})
    @Query("select a from Account a")
    List<Account> findAllEntityGraph();
}
