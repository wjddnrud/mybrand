package com.example.demo.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter @Setter
@Table(name = "orders")
@NoArgsConstructor(access = AccessLevel.PROTECTED)      // 직접 생성하면 안되고 다른 스타일로 생성해야하는구나 -> 생성 메서드를 이용해야하는구나 하고 소통할 수 있다.
public class Order {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")          //dba 분들이 선호하는 방식 '테이블명_id'
    private Long id;

    @ManyToOne(fetch = LAZY)  // 모든 연관관계는 지연로딩으로 설정할것!   // JPQL select o From order o; -> SQL select * from order N+1 (100 + 1 order)
    @JoinColumn(name = "member_id")  // join할 컬럼 -> 외래키 이름이 member_id가 된다.
    private Member member;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToOne(fetch = LAZY, cascade = CascadeType.ALL)           // 액세스를 자주 하는곳에 foreign키를 둔다
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    private LocalDateTime orderDate;            // LocalDateTime으로 타입을 지정하면 hibernate가 알아서 자동으로 지원해준다.

    @Enumerated(EnumType.STRING)
    private OrderStatus status;     // ORDER, CANCEL

    // [연관관계 편의 메서드]
    public void setMember(Member member) {
        this.member = member;
        member.getOrders().add(this);
    }

    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
        delivery.setOrder(this);
    }

    // [생성 메서드]
    // 복잡한 생성은 별도의 생성 메서드가 있으면 좋다.
    // OrderItem... => ...문법으로 작성하게되면 여러개를 받을수 있다.
    public static Order createOrder(Member member, Delivery delivery, OrderItem... orderItems) {
        Order order = new Order();
        order.setMember(member);
        order.setDelivery(delivery);
        for (OrderItem orderItem : orderItems) {
            order.addOrderItem(orderItem);
        }
        order.setStatus(OrderStatus.ORDER); // 처음 상태를 주문 상태로 강제시킨다.
        order.setOrderDate(LocalDateTime.now());
        return order;
    }

    // [비즈니스 로직]
    /**
     * 주문 취소
     */
    public void cancel() {
        if (delivery.getStatus() == DeliveryStatus.COMP) {
            throw new IllegalStateException("이미 배송 완료된 상품은 취소가 불가능합니다.");
        }

        this.setStatus(OrderStatus.CANCEL);
        for (OrderItem orderItem : orderItems) {
            orderItem.cancel();
        }
    }

    // [조회 로직]
    /**
     * 전체 주문 가격 조회
     * @return
     */
    public int getTotalPrice() {

//        int totalPrice = 0;
//        for (OrderItem orderItem : orderItems) {
//            totalPrice += orderItem.getTotalPrice();
//        }
//        return totalPrice;

        // java의 stream으로 바꿔서 위와 같은 로직을 아래와 같이 간결하게 작성할 수 있다.
        return orderItems.stream().mapToInt(OrderItem::getTotalPrice).sum();
    }


}
