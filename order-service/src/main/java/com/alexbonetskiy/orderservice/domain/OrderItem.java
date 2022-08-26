package com.alexbonetskiy.orderservice.domain;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "order_item")
@IdClass(OrderItemId.class)
public class OrderItem implements Serializable {

    @Serial
    private static final long serialVersionUID = 100L;

    @Id
    @ManyToOne
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    @JsonBackReference
    private Order order;

    @Id
    @ManyToOne
    @JoinColumn(name = "item_id", referencedColumnName = "id")
    private Item item;

    @NotNull
    @Min(value = 0)
    private int quantity;

    public OrderItem(Order order, Item item) {
        this.order = order;
        this.item = item;
    }

    protected OrderItem() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof OrderItem))
            return false;

        OrderItem that = (OrderItem) o;
        return Objects.equals(order, that.order) &&
                Objects.equals(item, that.item);
    }

    @Override
    public int hashCode() {
        return Objects.hash(order, item);
    }

    @Override
    public String toString() {
        return "OrderItem{" +
                "order=" + order +
                ", item=" + item +
                ", quantity=" + quantity +
                '}';
    }

    public Order getOrder() {
        return this.order;
    }

    public Item getItem() {
        return this.item;
    }

    public @NotNull @Positive int getQuantity() {
        return this.quantity;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public void setQuantity(@NotNull @Positive int quantity) {
        this.quantity = quantity;
    }
}
