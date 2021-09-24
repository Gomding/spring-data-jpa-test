package com.example.springdatajpatest;

import com.example.springdatajpatest.domain.Account;
import com.example.springdatajpatest.domain.Orders;
import com.example.springdatajpatest.domain.Price;
import com.example.springdatajpatest.domain.repository.AccountRepository;
import com.example.springdatajpatest.domain.repository.OrdersRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class CartesianProductTest {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private OrdersRepository ordersRepository;

    @Autowired
    private EntityManager entityManager;

    @BeforeEach
    void setUp() {
        for (int i = 0; i < 10; i++) {
            Account account = new Account("charlie" + i, "pmy", 3);
            account.addOrder(new Orders("productName" + i).setPrice(new Price(1000L)));
            account.addOrder(new Orders("chocolate" + i).setPrice(new Price(2000L)));
            Account savedAccount = accountRepository.save(account);
        }
    }

    @DisplayName("N+1 쿼리문제를 fetch join으로 해결하면 카테시안 곱이 발생할 수 있다. (inner join)")
    @Test
    void fetchJoin() {
        entityManager.flush();
        entityManager.clear();
        List<Account> accounts = accountRepository.findAllJoinFetch();

        assertThat(accounts).hasSize(20);

        System.out.println("accounts size : " + accounts.size());
        System.out.println("accounts : ");
        accounts.forEach(System.out::println);

        List<String> collect = accounts.stream()
                .flatMap(account -> account.getOrder().stream())
                .map(Orders::getProductName)
                .collect(Collectors.toList());

        collect.forEach(System.out::println);
    }

    @DisplayName("카테시안 곱 문제를 반환 컬렉션 타입을 Set으로 변경해서 해결한다.")
    @Test
    void setCollectionFetchJoin() {
        entityManager.flush();
        entityManager.clear();
        Set<Account> accounts = accountRepository.findAllSetJoinFetch();

        assertThat(accounts).hasSize(10);

        System.out.println("accounts size : " + accounts.size());
        System.out.println("accounts : ");
        accounts.forEach(System.out::println);

        List<String> collect = accounts.stream()
                .flatMap(account -> account.getOrder().stream())
                .map(Orders::getProductName)
                .collect(Collectors.toList());

        collect.forEach(System.out::println);
    }

    @DisplayName("카테시안 곱 문제를 fetch join 쿼리에 distinct를 추가해서 해결한다.")
    @Test
    void distinctFetchJoin() {
        entityManager.flush();
        entityManager.clear();
        List<Account> accounts = accountRepository.findAllDistinctJoinFetch();

        assertThat(accounts).hasSize(10);

        System.out.println("accounts size : " + accounts.size());
        System.out.println("accounts : ");
        accounts.forEach(System.out::println);

        List<String> collect = accounts.stream()
                .flatMap(account -> account.getOrder().stream())
                .map(Orders::getProductName)
                .collect(Collectors.toList());

        collect.forEach(System.out::println);
    }

    @DisplayName("N+1 쿼리문제를 entity graph로 해결하면 카테시안 곱이 발생할 수 있다.(outer join)")
    @Test
    void entityGraph() {
        entityManager.flush();
        entityManager.clear();
        List<Account> accounts = accountRepository.findAllEntityGraph();

        assertThat(accounts).hasSize(20);

        System.out.println("accounts size : " + accounts.size());
        System.out.println("accounts : ");
        accounts.forEach(System.out::println);

        List<String> collect = accounts.stream()
                .flatMap(account -> account.getOrder().stream())
                .map(Orders::getProductName)
                .collect(Collectors.toList());

        collect.forEach(System.out::println);
    }
}
