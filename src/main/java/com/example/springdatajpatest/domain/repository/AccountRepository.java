package com.example.springdatajpatest.domain.repository;

import com.example.springdatajpatest.domain.Account;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AccountRepository extends JpaRepository<Account, Long> {

    @Query("select a from Account a join fetch a.order")
    List<Account> findAllJoinFetch();

    @EntityGraph(attributePaths = {"order", "order.user"})
    @Query("select a from Account a")
    List<Account> findAllEntityGraph();

    List<Account> findByAccountName(String accountName);
}
