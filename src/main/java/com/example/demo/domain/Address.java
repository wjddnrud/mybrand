package com.example.demo.domain;

import jakarta.persistence.Embeddable;
import lombok.Getter;

@Embeddable //내장타입이라고 표시해주는것
@Getter
public class Address {

    private String city;
    private String street;
    private String zipcode;
}
