package com.alexbonetskiy.orderservice.service;

import com.alexbonetskiy.orderservice.client.ItemServiceClient;
import com.alexbonetskiy.orderservice.domain.Item;
import com.alexbonetskiy.orderservice.domain.Order;
import com.alexbonetskiy.orderservice.domain.OrderItem;
import com.alexbonetskiy.orderservice.domain.OrderStatus;
import com.alexbonetskiy.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;

    private final ItemServiceClient itemServiceClient;

    public List<Order> getAllOrders(int userId) {
        log.info("getAllOrders for userId={}", userId);
        return orderRepository.findAllByUserId(userId);
    }

    @Transactional
    public Order getCurrentOrder(int userId) {
        log.info("getCurrentOrder for userId={}", userId);
        Optional<Order> optionalOrder = orderRepository.getByUserIdAndOrderStatus(userId);
        optionalOrder.ifPresent(System.out::println);
        return optionalOrder.orElseGet(() -> new Order(userId, OrderStatus.CURRENT));
    }

    @Transactional
    public void addItem(int itemId, int qty) {
        log.info("addItem id={}, quantity={}", itemId, qty);
        Item item = itemServiceClient.getItemToOrder(itemId, qty);
        Order order = getCurrentOrder(1);
        List<OrderItem> orderItemList = order.getItems();
        for (OrderItem orderItem : orderItemList) {
            if (orderItem.getItem().equals(item))
                orderItem.setQuantity(orderItem.getQuantity() + qty);
            return;
        }
        order.addItemWithQuantity(item, qty);
        orderRepository.save(order);
    }

    @Transactional
    public Order confirmOrder() {
        Order order = getCurrentOrder(1);
        order.setOrderStatus(OrderStatus.CONFIRMED);
        return order;
    }
}
