package com.example.demo.domain;

import jakarta.persistence.Embeddable;
import lombok.Getter;

@Embeddable //내장타입이라고 표시해주는것
@Getter
public class Address {

    private String city;
    private String street;
    private String zipcode;


    // JPA 스펙상 엔티티나 임베디드 타입은 자바 기본 생성자를 public 또는 protected로 설정해야 한다.
//    protected Address() {
//    }
//
//    public Address(String city, String street, String zipcode) {
//        this.city = city;
//        this.street = street;
//        this.zipcode = zipcode;
//    }
}
