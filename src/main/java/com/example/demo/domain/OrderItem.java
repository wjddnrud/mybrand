package com.example.demo.domain;

import com.example.demo.domain.item.Item;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderItem {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    private int orderPrice;            // LINE :: 주문가격
    private int count;                 // LINE :: 주문 수량


    // 상단에 어노테이션을 @NoArgsConstructor(access = AccessLevel.PROTECTED) 이와같이 잡아줌으로서 생성 메서드를 사용하도록 유도하는 스타일로 짜는게 좋다.
//    protected OrderItem() {
//
//    }

    // [생성 메서드]
    public static OrderItem createOrderItem(Item item, int orderPrice, int count) {
        OrderItem orderItem = new OrderItem();
        orderItem.setItem(item);
        orderItem.setOrderPrice(orderPrice);
        orderItem.setCount(count);

        item.removeStock(count);
        return orderItem;
    }

    // [비즈니스 로직]
    public void cancel() {
        getItem().addStock(count);  // 주문 취소에 따라 재고 수량 원복 시켜주기
    }

    // [조회 로직]
    /**
     * 주문상품 전체 가격 조회
     * @return
     */
    public int getTotalPrice() {
        return getOrderPrice() * getCount();
    }
}
