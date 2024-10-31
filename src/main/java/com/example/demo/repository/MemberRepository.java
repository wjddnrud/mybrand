package com.example.demo.repository;

import com.example.demo.domain.MemberEntity;
import com.example.demo.domain.MemberEntity;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository         // 스프링에서 제공하는 이 어노테이션을 사용하면 컴포넌트 스캔에 의해서 자동으로 Spring 빈으로 관리가 된다.
@RequiredArgsConstructor
public class MemberRepository {

    // @PersistenceContext  // spring이 생성한 entityManager를 주입받기 위해 작성한다. // 원래는 안되지만 spring boot가 @Autowire도 인젝션 지원해주기 때문에 최종적으로 이와같이 사용할 수 있다.
    private final EntityManager em;

//    public MemberRepository(EntityManager em) {
//        this.em = em;
//    }

//    // 주석 : 직접 entityManageFactory를 사용하고 싶은 경우 아래와 같이 작성하여 사용하면 된다.
//    @PersistenceUnit
//    private EntityManagerFactory emf;


    // 주석 : 회원 등록 기능
    public void save(MemberEntity memberEntity) { em.persist(memberEntity); }

    // 주석 : 회원 단건 조회 기능
    // em.find(반환타입, PK)
    public MemberEntity findOne(Long id) { return em.find(MemberEntity.class, id); }

    // 주석 : 회원 전체 조회 기능
    // JPQL을 작성한다.  => em.createQuery("JPQL 쿼리문", 반환 타입).getResultList();  리스트로 만들어서 조회하는 메서드
    // JPQL과 SQL의 차이 SQL은 테이블 대상 JPQL은 엔티티 객체를 대상으로 쿼리가 실행된다.
    public List<MemberEntity> findAll() {
        return em.createQuery("select m from MemberEntity m", MemberEntity.class)
                .getResultList();
    }

    // 주석 : 회원 이름으로 조회 기능
    // 조건문 작성시 파라미터로 넘겨받은 name을 :name으로 작성하여 setParameter메서드를 이용해 바인딩 시켜준다.
    public List<MemberEntity> findByName(String name) {
        return em.createQuery("select m from MemberEntity m where m.name = :name", MemberEntity.class)
                .setParameter("name", name)
                .getResultList();
    }
}
