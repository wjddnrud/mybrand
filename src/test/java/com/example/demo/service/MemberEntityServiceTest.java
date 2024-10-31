package com.example.demo.service;


import com.example.demo.domain.MemberEntity;
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
class MemberEntityServiceTest {

    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;
    @Autowired EntityManager em;

    @Test
    public void 회원가입() throws Exception {
        //given (주어진것)
        MemberEntity memberEntity = new MemberEntity();   // 멤버 만들기
        memberEntity.setName("kim");

        //when (실행하면)
        Long savedId = memberService.join(memberEntity);

        //then (결과)
        assertEquals(memberEntity, memberRepository.findOne(savedId));
    }

    @Test
    public void 중복_회원_예외() throws Exception {
        //given
        MemberEntity memberEntity1 = new MemberEntity();
        memberEntity1.setName("kim");

        MemberEntity memberEntity2 = new MemberEntity();
        memberEntity2.setName("kim");

        //when
        memberService.join(memberEntity1);
        System.out.println("member2 name : " + memberEntity2.getName());
        assertThrows(IllegalStateException.class, () -> { memberService.join(memberEntity2);});
    }

}