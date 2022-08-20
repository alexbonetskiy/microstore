package com.alexbonetskiy.orderservice.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;


@NamedEntityGraph(
        name = "order-entity-graph-with-items",
        attributeNodes = {
                @NamedAttributeNode(value = "items", subgraph = "order-item-subgraph"),
        },
        subgraphs = {
                @NamedSubgraph(
                        name = "order-item-subgraph",
                        attributeNodes = {
                                @NamedAttributeNode("item")
                        }
                )
        }
)
@Entity
@Getter
@Setter
@Table(name = "orders")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order implements Serializable {

    @Serial
    private static final long serialVersionUID = 100L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "created_at", insertable = false)
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> items = new ArrayList<>();


    @Column(nullable = false, updatable = false)
    private Integer userId;

    @Enumerated
    OrderStatus orderStatus;

    public Order(Integer userId, OrderStatus orderStatus) {
        this.userId = userId;
        this.orderStatus = orderStatus;
    }

    public void addItem(Item item) {
        OrderItem orderItem = new OrderItem(this, item);
        items.add(orderItem);
        item.getOrders().add(orderItem);
    }

    public void addItemWithQuantity(Item item, int qty) {
        OrderItem orderItem = new OrderItem(this, item);
        orderItem.setQuantity(qty);
        items.add(orderItem);
        item.getOrders().add(orderItem);
    }

    public void removeItem(Item item) {
        for (Iterator<OrderItem> iterator = items.iterator();
             iterator.hasNext(); ) {
            OrderItem orderItem = iterator.next();

            if (orderItem.getOrder().equals(this) &&
                    orderItem.getItem().equals(item)) {
                iterator.remove();
                orderItem.getItem().getOrders().remove(orderItem);
                orderItem.setOrder(null);
                orderItem.setItem(null);
            }
        }
    }


    @PrePersist
    public  void prePersist() {
        if(this.orderStatus == OrderStatus.CONFIRMED)
        createdAt = LocalDateTime.now();
    }

    //https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof Order other))
            return false;

        return id != null &&
                id.equals(other.getId()) &&
                userId.equals(other.getUserId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId);
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", createdAt=" + createdAt +
                ", userId=" + userId +
                '}';
    }
}
