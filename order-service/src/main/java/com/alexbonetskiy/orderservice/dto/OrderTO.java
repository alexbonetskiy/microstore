package com.alexbonetskiy.orderservice.dto;

import com.alexbonetskiy.orderservice.domain.OrderItem;
import com.alexbonetskiy.orderservice.domain.OrderStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Value;

import javax.persistence.Enumerated;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Value
public class OrderTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 100L;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime lastModifiedAt;

    List<OrderItem> items = new ArrayList<>();

    OrderStatus orderStatus;
}
