package com.example.demo.service;

import com.example.demo.domain.Member;
import com.example.demo.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service        // 스프링에서 제공하는 이 어노테이션을 사용하면 컴포넌트 스캔이 포함되어있어 스프링 빈으로 등록이 된다.
@Transactional(readOnly = true) // 전체에 적용되지만 메서드 개별에도 적용 되어있으면 그것을 우선으로 한다.
@RequiredArgsConstructor    // final이 있는 필드만 가지고 생성자를 만들어 준다. 자동으로 전부 생성자를 만들려면 @AllArgument 어노테이션을 사용하면 된다.
public class MemberService {

    // @Autowired  // 필드 인젝션 : 스프링이 빈에 등록되어있는 레포지토리를 등록해준다.
    private final MemberRepository memberRepository;

    /**
     * 회원 가입
     * @param member
     * @return
     */
    @Transactional
    public Long join(Member member) {
        validateDuplicateMember(member);
        memberRepository.save(member);
        return member.getId();      // 저장된 id값이 뭔지 리턴해준다.
    }


    // 주석 : 중복 회원이 있는지 검사하는 로직
    // 주의 : 멀티스레드의 상황을 고려해서 데이터베이스에 member의 name을 unique제약조건을 걸어주는게 좋은 방법이다.
    private void validateDuplicateMember(Member member) {
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if (!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    /**
     * 회원 전체 조회
     */
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    /**
     * 회원 단건 조회
     * @param memberId
     * @return
     */
    public Member findOne(Long memberId) {
        return memberRepository.findOne(memberId);
    }


}
