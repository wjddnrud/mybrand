package com.example.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "member")
@Getter @Setter
public class MemberEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @NotEmpty
    private String name;                // LINE :: 회원명


    @Embedded   // 내장타입을 포함했다는 어노테이션으로 맵핑해주면된다.
    private Address address;            // LINE :: 주소 값타입

    @JsonIgnore // API가 순수하게 회원 정보만 뿌리고 싶을때 해당 어노테이션을 넣으면 값이 노출되지 않는다.
    @OneToMany(mappedBy = "memberEntity", cascade = CascadeType.ALL) // 읽기전용이 된다. member 필드에 의해서 나는 맵핑 된거야.
    private List<OrderEntity> orderEntities = new ArrayList<>();


}
