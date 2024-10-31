package com.example.demo.service;

import com.example.demo.domain.*;
import com.example.demo.domain.MemberEntity;
import com.example.demo.domain.OrderEntity;
import com.example.demo.domain.item.Book;
import com.example.demo.domain.item.ItemEntity;
import com.example.demo.exception.NotEnoughStockException;
import com.example.demo.repository.OrderRepository;
import jakarta.persistence.EntityManager;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class OrderEntityServiceTest {

    @Autowired EntityManager em;
    @Autowired OrderService orderService;
    @Autowired OrderRepository orderRepository;

    @Test
    public void 상품주문() throws Exception {
        //given
        MemberEntity memberEntity = createMember();

        Book book = createBook("시골 JPA", 10000, 10);

        //when
        int orderCount = 2;
        Long orderId = orderService.order(memberEntity.getId(), book.getId(), orderCount);

        //then
        OrderEntity getOrderEntity = orderRepository.findOne(orderId);

        assertEquals("상품 주문 시 상태는 ORDER", OrderStatus.ORDER, getOrderEntity.getStatus()); // 기대값, 실제값
        assertEquals("주문한 상품 종류 수가 정확해야 한다.", 1, getOrderEntity.getOrderItemEntities().size());
        assertEquals("주문 가격은 가격 * 수량이다.", 10000 * orderCount, getOrderEntity.getTotalPrice());
        assertEquals("주문 수량만큼 재고가 줄어야 한다.", 8, book.getStockQuantity());

    }

    @Test(expected = NotEnoughStockException.class)
    public void 상품주문_재고수량초과() throws Exception {
        //given
        MemberEntity memberEntity = createMember();
        ItemEntity itemEntity = createBook("시골 JPA", 10000, 10);

        int orderCount = 11;

        //when
        orderService.order(memberEntity.getId(), itemEntity.getId(), orderCount);   // 여기서 exception이 터져야 한다.

        //then
        fail("재고 수량 부족 예외가 발생해야 한다.");  // 이 라인까지 오면 안되는구나하고 알려줄 수 있다.
    }

    @Test
    public void 주문취소() throws Exception {
        //given
        MemberEntity memberEntity = createMember();
        Book item = createBook("시골 JPA", 10000, 10);

        int orderCount = 2;

        Long orderId = orderService.order(memberEntity.getId(), item.getId(), orderCount);

        //when
        orderService.cancelOrder(orderId);

        //then
        OrderEntity getOrderEntity = orderRepository.findOne(orderId);

        assertEquals("주문 취소 시 상태는 CANCEL이다.", OrderStatus.CANCEL, getOrderEntity.getStatus());
        assertEquals("주문이 취소된 상품은 그 만큼 재고가 증가해야 한다.", 10, item.getStockQuantity());
    }

    private Book createBook(String name, int price, int stockQuantity) {
        Book book = new Book();
        book.setName(name);
        book.setPrice(price);
        book.setStockQuantity(stockQuantity);
        em.persist(book);
        return book;
    }

    private MemberEntity createMember() {
        MemberEntity memberEntity = new MemberEntity();
        memberEntity.setName("회원1");
        memberEntity.setAddress(new Address("서울", "경기", "123-123"));
        em.persist(memberEntity);
        return memberEntity;
    }
}