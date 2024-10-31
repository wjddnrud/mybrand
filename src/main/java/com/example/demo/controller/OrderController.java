package com.example.demo.controller;

import com.example.demo.domain.MemberEntity;
import com.example.demo.domain.OrderEntity;
import com.example.demo.domain.item.ItemEntity;
import com.example.demo.repository.OrderSearch;
import com.example.demo.service.ItemService;
import com.example.demo.service.MemberService;
import com.example.demo.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final MemberService memberService;
    private final ItemService itemService;


    @GetMapping("/order")
    public String createForm(Model model) {

        List<MemberEntity> memberEntities = memberService.findMembers();
        List<ItemEntity> itemEntities = itemService.findItems();

        model.addAttribute("members", memberEntities);
        model.addAttribute("items", itemEntities);

        return "order/orderForm";
    }

    @PostMapping("/order")
    public String order(@RequestParam("memberId") Long memberId,
                        @RequestParam("itemId") Long itemId,
                        @RequestParam("count") int count) {
        // 주석 : html에서 값 받아서 넘겨주는 name으로 @RequestParam으로 변수에 바인딩 해주면 된다.
        orderService.order(memberId, itemId, count);
        return "redirect:/orders";
    }

    @GetMapping("/orders")
    public String orderList(@ModelAttribute("orderSearch") OrderSearch orderSearch, Model model) {
        List<OrderEntity> orderEntities = orderService.findOrders(orderSearch);
        model.addAttribute("orders", orderEntities);
        return "order/orderList";
    }

    @PostMapping("/orders/{orderId}/cancel")
    public String cancelOrder(@PathVariable("orderId") Long orderId) {
        orderService.cancelOrder(orderId);
        return "redirect:/orders";
    }


}
