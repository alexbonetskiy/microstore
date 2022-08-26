package com.alexbonetskiy.orderservice.repository;

import com.alexbonetskiy.orderservice.domain.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface OrderItemRepository extends JpaRepository<OrderItem, Integer> {

    @Modifying
    @Query("DELETE FROM OrderItem oi Where oi.order.id =:id and oi.item.id =:id1")
    void deleteByCompositeId(Integer id, Integer id1);
}
