package com.alexbonetskiy.orderservice.repository;

import com.alexbonetskiy.orderservice.domain.Order;
import com.alexbonetskiy.orderservice.domain.OrderStatus;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface OrderRepository extends JpaRepository<Order, Integer> {

    List<Order> findAllByUserId(int id);

    @EntityGraph(value = "order-entity-graph-with-items", type = EntityGraph.EntityGraphType.LOAD)
    Optional<Order> findByUserIdAndOrderStatus(int id, OrderStatus status);

    List<Order> findAllByOrderStatus(OrderStatus status);


    default Optional<Order> getByUserIdAndOrderStatus(int id) {
        return findByUserIdAndOrderStatus(id, OrderStatus.CURRENT);
    }

}