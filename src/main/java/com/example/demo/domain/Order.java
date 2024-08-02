package com.example.demo.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.codehaus.groovy.ast.expr.UnaryMinusExpression;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter @Setter
@Table(name = "orders")
public class Order {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")          //dba 분들이 선호하는 방식 '테이블명_id'
    private Long id;

    @ManyToOne(fetch = LAZY)  // 모든 연관관계는 지연로딩으로 설정할것!   // JPQL select o From order o; -> SQL select * from order N+1 (100 + 1 order)
    @JoinColumn(name = "member_id")  // join할 컬럼 -> 외래키 이름이 member_id가 된다.
    private Member member;

    @OneToMany(mappedBy = "order")
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToOne           // 액세스를 자주 하는곳에 foreign키를 둔다
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    private LocalDateTime orderDate;            // LocalDateTime으로 타입을 지정하면 hibernate가 알아서 자동으로 지원해준다.

    @Enumerated(EnumType.STRING)
    private OrderStatus status;     // ORDER, CANCEL



}
