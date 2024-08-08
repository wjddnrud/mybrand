package com.example.demo.repositoryByInterface;

import com.example.demo.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    // 주석 : 레포지토리를 interface로 구현하게 되면 JpaRepository를 구현체로 사용하지 않고 바로 상속받아 사용할 수 있다.
    // 이미 제공되는 메서드들을 가져다 사용하려면 이와같이 구현하면 되지만, 종종 직접 메서드를 구현해야할 때가 있고 그럴때는 클래스에 인터페이스를 implements(구현)하여 사용해야한다.

    Member save(Member member);

    List<Member> findAll();

    Member findByName(Member member);

}
