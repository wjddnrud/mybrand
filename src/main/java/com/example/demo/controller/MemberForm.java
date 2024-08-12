package com.example.demo.controller;

import com.example.demo.domain.Member;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MemberForm {

    private Long id;

    @NotEmpty(message = "회원 이름은 필수 입니다.")
    private String name;

    private String city;
    private String street;
    private String zipcode;

    public static MemberForm toMemberForm(Member member) {
        MemberForm memberForm = new MemberForm();
        memberForm.id = member.getId();
        memberForm.name = member.getName();
        memberForm.city = member.getAddress().getCity();
        memberForm.street = member.getAddress().getStreet();
        memberForm.zipcode = member.getAddress().getZipcode();
        return memberForm;
    }
}
