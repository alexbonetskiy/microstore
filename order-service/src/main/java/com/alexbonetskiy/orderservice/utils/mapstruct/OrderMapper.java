package com.alexbonetskiy.orderservice.utils.mapstruct;


import com.alexbonetskiy.orderservice.domain.Order;
import com.alexbonetskiy.orderservice.dto.OrderTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;


@Mapper
public interface OrderMapper {

    OrderMapper ORDER_MAPPER = Mappers.getMapper(OrderMapper.class);

    Order order(OrderTO orderTO);

    OrderTO orderTO(Order order);

}