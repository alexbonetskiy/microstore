package com.alexbonetskiy.orderservice.controller;


import com.alexbonetskiy.orderservice.domain.Order;
import com.alexbonetskiy.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
@Slf4j
public class OrderController {

    private final OrderService orderService;


    @GetMapping
    public List<Order> getAllOrders(int userId) {
        log.info("getAllOrders for userId={}", userId);
        return orderService.getAllOrders(userId);
    }

    @GetMapping("/current/{id}")
    public ResponseEntity<Order> getCurrentOrder(@PathVariable("id") int userId) {
        log.info("getCurrentOrder for userId={}", userId);
        return ResponseEntity.ok(orderService.getCurrentOrder(userId));
    }

    @PostMapping(value = "/current/basket", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void addItemToOrder(@RequestParam("id") int itemId, @RequestParam("quantity") int qty) {
         orderService.addItem(itemId, qty);
    }

    @PostMapping(value = "/current", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Order confirmOrder() {
       return orderService.confirmOrder();
    }

}
