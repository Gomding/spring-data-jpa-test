package com.example.springdatajpatest.service;

import com.example.springdatajpatest.domain.Orders;
import com.example.springdatajpatest.domain.repository.OrdersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import javax.persistence.EntityManager;

@Service
public class DeliveryService {

    @Autowired
    EntityManager entityManager;
    @Autowired
    OrdersRepository ordersRepository;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void toDoSomething() throws InterruptedException {
        System.out.println("DeliveryService toDoSomething Method start!!!");

        Thread thread = Thread.currentThread();
        System.out.println(thread.getName());
        System.out.println(thread.getClass());
        Orders order = new Orders("빼뺴로");
        ordersRepository.save(order);
        entityManager.flush();

        Thread.sleep(100000);

        System.out.println("Transaction name = " + TransactionSynchronizationManager.getCurrentTransactionName());

        System.out.println("DeliveryService toDoSomething Method finish!!!");
    }
}
