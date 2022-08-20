package com.alexbonetskiy.orderservice.client;

import com.alexbonetskiy.orderservice.domain.Item;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient(name = "item-service")
public interface ItemServiceClient {

@PostMapping(value = "/items/basket", consumes = MediaType.APPLICATION_JSON_VALUE)
    Item getItemToOrder(@RequestParam int id, @RequestParam int qty);
}


