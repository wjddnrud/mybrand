package com.example.demo.controller;

import com.example.demo.domain.MemberEntity;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MemberDto {

    private Long id;

    @NotEmpty(message = "회원 이름은 필수 입니다.")
    private String name;

    private String city;
    private String street;
    private String zipcode;

    public static MemberDto toMemberForm(MemberEntity memberEntity) {
        MemberDto memberDto = new MemberDto();
        memberDto.id = memberEntity.getId();
        memberDto.name = memberEntity.getName();
        memberDto.city = memberEntity.getAddress().getCity();
        memberDto.street = memberEntity.getAddress().getStreet();
        memberDto.zipcode = memberEntity.getAddress().getZipcode();
        return memberDto;
    }
}
