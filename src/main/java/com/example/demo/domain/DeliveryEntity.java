package com.example.demo.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Table(name = "delivery")
@Getter @Setter
public class DeliveryEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "delivery_id")
    private Long id;

    @OneToOne(mappedBy = "deliveryEntity", fetch = LAZY, cascade = CascadeType.ALL)       // 액세스를 자주 하는곳에 foreign키를 둔다
    private OrderEntity orderEntity;

    @Embedded
    private Address address;

    @Enumerated(EnumType.STRING)        // 중간의 다른 상태가 생길경우 숫자로 분류하기가 어려워지기 떄문에 Ordinal 사용 x
    private DeliveryStatus status;      // READY, COMP
}
