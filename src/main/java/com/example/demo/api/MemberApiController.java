package com.example.demo.api;

import com.example.demo.domain.MemberEntity;
import com.example.demo.service.MemberService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class MemberApiController {

    private final MemberService memberService;

    /**
     * 회원 등록 - 데이터를 엔티티로 받은 버전 (엔티티 노출)
     * @param member
     * @return
     */
    @PostMapping("api/v1/members")
    public CreateMemberResponse saveMemberV1(@RequestBody @Valid MemberEntity member) {
        Long id = memberService.join(member);
        return new CreateMemberResponse(id);
    }

    /**
     * 회원 등록 - 데이터를 DTO로 받은 버전 -> API로 작성할 경우 Entity를 외부에 노출 시키면 안된다. (= API 스펙에 Entity를 그대로 사용하면 안된다.)
     * @param request
     * @return
     */
    @PostMapping("api/v2/members")
    public CreateMemberResponse saveMemberV2(@RequestBody @Valid CreateMemberRequest request) {
        MemberEntity member = new MemberEntity();
        member.setName(request.getName());

        Long id = memberService.join(member);
        return new CreateMemberResponse(id);
    }

    /**
     * 회원 수정
     * @param id
     * @param request
     * @return
     */
    @PutMapping("api/v2/members/{id}")
    public UpdateMemberResponse updateMemberResponseV2(
            @PathVariable("id") Long id,
            @RequestBody @Valid UpdateMemberRequest request) {
        memberService.update(id, request.getName());
        MemberEntity findMember = memberService.findOne(id);
        return new UpdateMemberResponse(findMember.getId(), findMember.getName());
    }

    @Data
    static class UpdateMemberRequest {
        private String name;
    }

    @Data
    @AllArgsConstructor
    static class UpdateMemberResponse {
        private Long id;
        private String name;
    }

    @Data
    static class CreateMemberRequest {
        private String name;
    }

    @Data
    static class CreateMemberResponse {
        private Long id;

        public CreateMemberResponse(Long id) {
            this.id = id;
        }
    }


}
