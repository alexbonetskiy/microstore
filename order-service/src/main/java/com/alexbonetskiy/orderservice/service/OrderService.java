package com.alexbonetskiy.orderservice.service;

import com.alexbonetskiy.orderservice.client.ItemServiceClient;
import com.alexbonetskiy.orderservice.domain.Item;
import com.alexbonetskiy.orderservice.domain.Order;
import com.alexbonetskiy.orderservice.domain.OrderItem;
import com.alexbonetskiy.orderservice.domain.OrderStatus;
import com.alexbonetskiy.orderservice.dto.ItemTO;
import com.alexbonetskiy.orderservice.dto.OrderTO;
import com.alexbonetskiy.orderservice.exception.IllegalRequestDataException;
import com.alexbonetskiy.orderservice.repository.ItemRepository;
import com.alexbonetskiy.orderservice.repository.OrderItemRepository;
import com.alexbonetskiy.orderservice.repository.OrderRepository;
import com.alexbonetskiy.orderservice.utils.mapstruct.ItemMapper;
import com.alexbonetskiy.orderservice.utils.mapstruct.OrderMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;

    private final ItemRepository itemRepository;

    private final ItemServiceClient itemServiceClient;

    private final OrderItemRepository orderItemRepository;

    public List<Order> getAllOrders(int userId) {
        log.info("getAllOrders for userId={}", userId);
        return orderRepository.findAllByUserId(userId);
    }

    @Transactional
    public OrderTO getCurrentOrder(int userId) {
        log.info("getCurrentOrder for userId={}", userId);
        Optional<Order> optionalOrder = orderRepository.getByUserIdAndOrderStatus(userId);
        OrderTO orderTO;
        if (optionalOrder.isPresent()) {
            orderTO = OrderMapper.ORDER_MAPPER.orderTO(optionalOrder.get());
            return orderTO;
        } else return new OrderTO(LocalDateTime.now(), OrderStatus.CURRENT);
    }

    @Transactional
    public OrderTO addItemToOrder(int itemId, int qty, int userId) {
        log.info("add item id={}, quantity={}", itemId, qty);
        ItemTO itemTO = itemServiceClient.getItemToOrder(itemId, qty);
        if (itemTO.getException() != null) throw new IllegalRequestDataException(itemTO.getException());
        else {
            Item item = getActualItemFromDb(itemTO);
            Optional<Order> optionalOrder = orderRepository.getByUserIdAndOrderStatus(1);
            Order order = optionalOrder.orElse(new Order(userId, OrderStatus.CURRENT));
            List<OrderItem> orderItemList = order.getItems();
            for (OrderItem orderItem : orderItemList) {
                if (orderItem.getItem().equals(item)) {
                    orderItem.setQuantity(orderItem.getQuantity() + qty);
                    return OrderMapper.ORDER_MAPPER.orderTO(orderRepository.save(order));
                }
            }
            order.addItemWithQuantity(item, qty);
            return OrderMapper.ORDER_MAPPER.orderTO(orderRepository.save(order));
        }
    }

    @Transactional
    public void deleteItemFromOrder(int itemId, int userId) {
        log.info("delete item id={} from basket of userId={}", itemId, userId);
        Optional<Order> optionalOrder = orderRepository.getByUserIdAndOrderStatus(userId);
        Order order = optionalOrder.orElseThrow(() -> new IllegalRequestDataException("Basket is empty"));
        List<OrderItem> orderItemList = new ArrayList<>();
        for (OrderItem orderItem : order.getItems()) {
            if (orderItem.getItem().getCatalogId().equals(itemId)) {
                orderItemList.add(orderItem);
                orderItemRepository.delete(orderItem);
                if (orderItem.getItem().getOrders().isEmpty()) {
                    itemRepository.delete(orderItem.getItem());
                }
                order.removeItem(orderItem.getItem());

                return;
            }
        }
        throw new IllegalRequestDataException("There is no item with id=" + itemId + " in current order");
    }

    @Transactional
    public OrderTO confirmOrder(int userId) {
        log.info("confirm order");
        Optional<Order> optionalOrder = orderRepository.getByUserIdAndOrderStatus(userId);
        Order order = optionalOrder.orElseThrow(() -> new IllegalRequestDataException("Basket is empty"));
        if (order.getItems().isEmpty()) throw new IllegalRequestDataException("Basket is empty");
        int counter = 0;
        for (OrderItem orderItem : order.getItems()) {
            ItemTO itemTO = itemServiceClient.reserveItem(orderItem.getItem().getCatalogId(), orderItem.getQuantity());
            if (itemTO.getException() != null) {
                while (counter > 0) {
                    itemServiceClient.rejectPurchase(order.getItems().get(counter).getItem().getCatalogId(), order.getItems().get(counter).getQuantity());
                    counter--;
                }
                throw new IllegalRequestDataException("Item id=" + orderItem.getItem().getCatalogId() + " is out of stock");
            }
            counter++;
        }
        order.setOrderStatus(OrderStatus.CONFIRMED);
        return OrderMapper.ORDER_MAPPER.orderTO(order);
    }


    @Transactional
    public void updateItemInCurrentOrders(ItemTO itemTO) {
        log.info("update item id={}", itemTO.getId());
        List<Order> orderList = orderRepository.findAllByOrderStatus(OrderStatus.CURRENT);
        Item item = getActualItemFromDb(itemTO);
        if (!orderList.isEmpty()) {
            for (Order order : orderList) {
                Optional<OrderItem> removedOrderItemOptional = order.getItems().stream().filter(orderItem -> orderItem.getItem().getCatalogId().equals(item.getCatalogId())).findFirst();
                if (removedOrderItemOptional.isPresent()) {
                    OrderItem removedOrderItem = removedOrderItemOptional.get();
                    order.addItemWithQuantity(item, removedOrderItem.getQuantity());
                    orderItemRepository.delete(removedOrderItem);
                    if (removedOrderItem.getItem().getOrders().isEmpty()) {
                        itemRepository.delete(removedOrderItem.getItem());
                    }
                    order.removeItem(removedOrderItem.getItem());
                }
            }
        }
    }

    @Transactional
    public Item getActualItemFromDb(ItemTO itemTO) {
        Item item = itemRepository.getByCatalogIdAndNameAndDescriptionAndPrice(itemTO.getId(), itemTO.getName(), itemTO.getDescription(), itemTO.getPrice());
        if (item != null)
            return item;
        else {
            item = ItemMapper.ITEM_MAPPER.item(itemTO);
            return itemRepository.save(item);
        }
    }

}
