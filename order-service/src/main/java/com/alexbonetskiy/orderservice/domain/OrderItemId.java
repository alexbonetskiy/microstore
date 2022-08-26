package com.alexbonetskiy.orderservice.domain;

import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;


@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class OrderItemId implements Serializable {

    @Serial
    private static final long serialVersionUID = 100L;

    Integer order;

    Integer item;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof OrderItemId))
            return false;

        OrderItemId that = (OrderItemId) o;
        return Objects.equals(order, that.order) &&
                Objects.equals(item, that.item);
    }

    @Override
    public int hashCode() {
        return Objects.hash(order, item);
    }

}
