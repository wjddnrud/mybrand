package com.example.demo.service;

import com.example.demo.domain.DeliveryEntity;
import com.example.demo.domain.MemberEntity;
import com.example.demo.domain.OrderEntity;
import com.example.demo.domain.OrderItemEntity;
import com.example.demo.domain.item.ItemEntity;
import com.example.demo.repository.ItemRepository;
import com.example.demo.repository.MemberRepository;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.OrderSearch;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;

    // 주문
    @Transactional
    public Long order(Long memberId, Long itemId, int count) {

        // 엔티티 조회
        MemberEntity memberEntity = memberRepository.findOne(memberId);
        ItemEntity itemEntity = itemRepository.findOne(itemId);

        // 배송정보 생성
        DeliveryEntity deliveryEntity = new DeliveryEntity();
        deliveryEntity.setAddress(memberEntity.getAddress());

        // 주문상품 생성
        OrderItemEntity orderItemEntity = OrderItemEntity.createOrderItem(itemEntity, itemEntity.getPrice(), count);

        // 주문 생성
        OrderEntity orderEntity = OrderEntity.createOrder(memberEntity, deliveryEntity, orderItemEntity);

        // 주문 저장
        orderRepository.save(orderEntity);                    // => order를 persist하면 orderItem도, delivery도 persist한다 cascade 때문에

        return orderEntity.getId();
    }

    // 취소
    @Transactional
    public void cancelOrder(Long orderId) {
        // 주문 엔티티 조회
        OrderEntity orderEntity = orderRepository.findOne(orderId);

        // 주문 취소
        orderEntity.cancel();
    }

    // 검색
    public List<OrderEntity> findOrders(OrderSearch orderSearch) {
        return orderRepository.findAll(orderSearch);
    }

}
