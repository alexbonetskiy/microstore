package com.alexbonetskiy.orderservice.web;


import com.alexbonetskiy.orderservice.domain.Order;
import com.alexbonetskiy.orderservice.dto.ItemTO;
import com.alexbonetskiy.orderservice.dto.OrderTO;
import com.alexbonetskiy.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = OrderController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Slf4j
public class OrderController {

    public static final String REST_URL = "/orders";

    private final OrderService orderService;


    @GetMapping
    public List<Order> getAllOrders(int userId) {
        log.info("getAllOrders for userId={}", userId);
        return orderService.getAllOrders(userId);
    }

    @GetMapping("/current")
    public ResponseEntity<OrderTO> getCurrentOrder(@RequestParam int userId) {
        log.info("getCurrentOrder for userId={}", userId);
        return ResponseEntity.ok(orderService.getCurrentOrder(userId));
    }

    @PostMapping(value = "/current/basket", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OrderTO> addItemToOrder(@RequestParam("id") int itemId, @RequestParam("quantity") int qty) {
        return ResponseEntity.ok(orderService.addItemToOrder(itemId, qty));
    }

    @DeleteMapping("/current/basket/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteItemFromOrder(@PathVariable("id") int itemId) {
        log.info("delete item id={}", itemId);
        orderService.deleteItemFromOrder(itemId);
    }


    @PostMapping(value = "/current/confirm", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OrderTO> confirmOrder() {
        log.info("confirm order");
        return ResponseEntity.ok(orderService.confirmOrder());
    }

    @PostMapping(value = "/current", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void updateItemInCurrentOrders(@RequestBody ItemTO itemTO) {
        log.info("update item id={}", itemTO.getId());
        orderService.updateItemInCurrentOrders(itemTO);

    }

}
