package com.example.demo.controller;

import com.example.demo.domain.Address;
import com.example.demo.domain.Member;
import com.example.demo.service.MemberService;
import jakarta.validation.Valid;            // form에 있는 @NotEmpty 어노테이션을 쓰는구나 하고 적용시킨다.
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/members/new")
    public String createForm(Model model) {
        model.addAttribute("memberForm", new MemberForm());
        return "members/createMemberForm";
    }

    @PostMapping("members/new")
    public String create(@Valid MemberForm form, BindingResult result) {
        // 첫번째 파라미터에서 발생한 오류를 넘겨 받아 입력값 체크를 할 수 있다.

        if (result.hasErrors()) {
            return "members/createMemberForm";
        }

        Address address = new Address(form.getCity(), form.getStreet(), form.getZipcode());

        Member member = new Member();
        member.setName(form.getName());
        member.setAddress(address);

        memberService.join(member);
        return "redirect:/";
    }

    @GetMapping("/members")
    public String list(Model model) {
        List<Member> members = memberService.findMembers();
        // 주석 : 아래와 같이 Entity를 그대로 전달하는것 보다 DTO로 변환해서 넘겨주어야 한다.
        List<MemberForm> memberForms = (members.stream().map(MemberForm::toMemberForm).toList());
        model.addAttribute("members", memberForms);
        return "members/memberList";
    }


}
