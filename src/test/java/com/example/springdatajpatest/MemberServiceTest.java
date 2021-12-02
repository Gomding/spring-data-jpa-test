package com.example.springdatajpatest;

import com.example.springdatajpatest.service.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MemberServiceTest {

    @Autowired
    MemberService memberService;

    /**
     * 트랜잭션 전파속성이 REQUIRES_NEW 일 때 스레드도 새로 할당할까? 커넥션은?
     *
     * 결론 : 스레드는 새로 생성하지 않음
     *       트랜잭션을 새로 열면서 커넥션을 꺼내씀
     *       즉, REQUIRED 트랜잭션 1개 + 커넥션 1개 REQUIRES_NEW 트랜잭션 1개 + 커넥션 1개
     *       총 트랜잭션 2개 커넥션 2개
     */
    @DisplayName("트랜잭션 전파속성 REQUIRES_NEW 사용시 스레드는 어떻게 사용하는가")
    @Test
    void requires_new_thread_test() throws InterruptedException {
        memberService.toDoSomething();
    }
}
