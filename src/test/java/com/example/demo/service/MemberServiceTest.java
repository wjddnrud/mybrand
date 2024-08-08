package com.example.demo.service;


import com.example.demo.domain.Member;
import com.example.demo.repository.MemberRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)    // Junit 실행할 때 스프링이랑 같이 엮어서 실행할래 라고 하면 이 @Runwith에 springRunner.class를 넣어준다.
@SpringBootTest // 스플이 부트를 띄운 상태로 뭔가 테스트를 하려면 이게 있어야 한다. 이게 없으면 오토와이어가 다 실패한다. 스프링 컨테이너 안에서 이 테스트를 돌리는 것이다.
// 위 두 어노테이션이 있어야 스프링이랑 인티그레이션 해서 스프링 부트를 실제 딱 올려서 테스트를 할 수 있다.
@Transactional
class MemberServiceTest {

    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;
    @Autowired EntityManager em;

    @Test
    public void 회원가입() throws Exception {
        //given (주어진것)
        Member member = new Member();   // 멤버 만들기
        member.setName("kim");

        //when (실행하면)
        Long savedId = memberService.join(member);

        //then (결과)
        assertEquals(member, memberRepository.findOne(savedId));
    }

    @Test
    public void 중복_회원_예외() throws Exception {
        //given
        Member member1 = new Member();
        member1.setName("kim");

        Member member2 = new Member();
        member2.setName("kim");

        //when
        memberService.join(member1);
        System.out.println("member2 name : " + member2.getName());
        assertThrows(IllegalStateException.class, () -> { memberService.join(member2);});
    }

}