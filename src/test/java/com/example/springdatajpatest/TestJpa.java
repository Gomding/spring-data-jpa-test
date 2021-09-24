package com.example.springdatajpatest;

import com.example.springdatajpatest.domain.Account;
import com.example.springdatajpatest.domain.Orders;
import com.example.springdatajpatest.domain.repository.AccountRepository;
import com.example.springdatajpatest.domain.repository.OrdersRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.stream.Collectors;

@DataJpaTest
public class TestJpa {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private OrdersRepository ordersRepository;

    @Autowired
    private EntityManager entityManager;

    @BeforeEach
    void setUp() {
        for (int i = 0; i < 10; i++) {
            Account account = new Account("charlie" + i, "pmy", 3).addOrder(new Orders("productName" + i));
            Account savedAccount = accountRepository.save(account);
        }
    }

    @DisplayName("N+1 쿼리문제가 발생한다.")
    @Test
    void test() {
        entityManager.flush();
        entityManager.clear();
        List<Account> accounts = accountRepository.findAll();

        List<String> collect = accounts.stream()
                .map(account -> account.getOrder().get(0).getProductName())
                .collect(Collectors.toList());

        collect.forEach(System.out::println);
    }

    @DisplayName("N+1 쿼리문제를 fetch join으로 해결한다.")
    @Test
    void test2() {
        entityManager.flush();
        entityManager.clear();
        List<Account> accounts = accountRepository.findAllJoinFetch();

        System.out.println("accounts size : " + accounts.size());

        List<String> collect = accounts.stream()
                .map(account -> account.getOrder().get(0).getProductName())
                .collect(Collectors.toList());

        collect.forEach(System.out::println);
    }

    @DisplayName("N+1 쿼리문제를 entity graph로 해결한다.")
    @Test
    void test3() {
        entityManager.flush();
        entityManager.clear();
        List<Account> accounts = accountRepository.findAllEntityGraph();

        System.out.println("accounts size : " + accounts.size());

        List<String> collect = accounts.stream()
                .map(account -> account.getOrder().get(0).getProductName())
                .collect(Collectors.toList());

        collect.forEach(System.out::println);
    }
}
