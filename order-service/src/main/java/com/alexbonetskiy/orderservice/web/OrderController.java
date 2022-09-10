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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
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
    public List<Order> getAllOrders(@AuthenticationPrincipal Jwt jwt) {
        log.info("getAllOrders for userId={}", jwt.getClaims().get("user_id"));
        return orderService.getAllOrders(Integer.parseInt((String) jwt.getClaims().get("user_id")));
    }

    @GetMapping("/current")
    public ResponseEntity<OrderTO> getCurrentOrder(@AuthenticationPrincipal Jwt jwt) {
        log.info("getCurrentOrder for userId={}", jwt.getClaims().get("user_id"));
        return ResponseEntity.ok(orderService.getCurrentOrder(Integer.parseInt((String) jwt.getClaims().get("user_id"))));
    }

    @PostMapping(value = "/current/basket")
    public ResponseEntity<OrderTO> addItemToOrder(@RequestParam("id") int itemId, @RequestParam("quantity") int qty, @AuthenticationPrincipal Jwt jwt) {
        log.info("addItemToOrder for itemId={}, qty={}, userId={}", itemId, qty, jwt.getClaims().get("user_id"));
        return ResponseEntity.ok(orderService.addItemToOrder(itemId, qty, Integer.parseInt((String) jwt.getClaims().get("user_id"))));
    }

    @DeleteMapping("/current/basket/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteItemFromOrder(@PathVariable("id") int itemId, @AuthenticationPrincipal Jwt jwt) {
        log.info("delete item id={} from basket of userId={}", itemId, jwt.getClaims().get("user_id"));
        orderService.deleteItemFromOrder(itemId, Integer.parseInt((String) jwt.getClaims().get("user_id")));
    }

    @PostMapping(value = "/current/confirm")
    public ResponseEntity<OrderTO> confirmOrder(@AuthenticationPrincipal Jwt jwt) {
        log.info("confirm order of userId={}", jwt.getClaims().get("user_id"));
        return ResponseEntity.ok(orderService.confirmOrder(Integer.parseInt((String) jwt.getClaims().get("user_id"))));
    }

    @PreAuthorize("hasAuthority('SCOPE_server')")
    @PostMapping(value = "/current", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void updateItemInCurrentOrders(@RequestBody ItemTO itemTO) {
        log.info("update item id={}", itemTO.getId());
        orderService.updateItemInCurrentOrders(itemTO);

    }

}
