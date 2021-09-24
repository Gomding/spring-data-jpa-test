package com.example.springdatajpatest;

import com.example.springdatajpatest.domain.Account;
import com.example.springdatajpatest.domain.Order;
import com.example.springdatajpatest.domain.Price;
import com.example.springdatajpatest.domain.repository.AccountRepository;
import com.example.springdatajpatest.domain.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.stream.Collectors;

@DataJpaTest
class CartesianProductTest {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private EntityManager entityManager;

    @BeforeEach
    void setUp() {
        for (int i = 0; i < 10; i++) {
            Account account = new Account("charlie" + i, "pmy", 3);
            account.addOrder(new Order("productName" + i).setPrice(new Price(1000L)));
            account.addOrder(new Order("chocolate" + i).setPrice(new Price(2000L)));
            Account savedAccount = accountRepository.save(account);
        }
    }

    @DisplayName("N+1 쿼리문제를 fetch join으로 해결하면 카테시안 곱이 발생할 수 있다. (inner join)")
    @Test
    void fetchJoin() {
        entityManager.flush();
        entityManager.clear();
        List<Account> accounts = accountRepository.findAllJoinFetch();

        System.out.println("accounts size : " + accounts.size());
        System.out.println("accounts : ");
        accounts.forEach(System.out::println);

        List<String> collect = accounts.stream()
                .flatMap(account -> account.getOrder().stream())
                .map(Order::getProduct)
                .collect(Collectors.toList());

        collect.forEach(System.out::println);
    }

    @DisplayName("N+1 쿼리문제를 entity graph로 해결하면 카테시안 곱이 발생할 수 있다.(outer join)")
    @Test
    void entityGraph() {
        entityManager.flush();
        entityManager.clear();
        List<Account> accounts = accountRepository.findAllEntityGraph();

        System.out.println("accounts size : " + accounts.size());
        System.out.println("accounts : ");
        accounts.forEach(System.out::println);

        List<String> collect = accounts.stream()
                .flatMap(account -> account.getOrder().stream())
                .map(Order::getProduct)
                .collect(Collectors.toList());

        collect.forEach(System.out::println);
    }
}
