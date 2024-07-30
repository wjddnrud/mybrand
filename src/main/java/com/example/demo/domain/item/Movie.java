package com.example.demo.domain.item;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@DiscriminatorValue("M")    //기본적으로 테이블명으로 들어감
@Getter @Setter
public class Movie extends Item {

    private String director;
    private String actor;

}
