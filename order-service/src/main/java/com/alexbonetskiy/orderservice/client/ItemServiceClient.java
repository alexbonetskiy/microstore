package com.alexbonetskiy.orderservice.client;

import com.alexbonetskiy.orderservice.dto.ItemTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient(name = "item-service")
public interface ItemServiceClient {

    @GetMapping("/items/basket")
    ItemTO getItemToOrder(@RequestParam int id, @RequestParam int qty);

    @PostMapping(value = "/items/basket/confirm", consumes = MediaType.APPLICATION_JSON_VALUE)
    ItemTO reserveItem(@RequestParam int id, @RequestParam int qty);

    @PostMapping(value = "/items/basket/reject", consumes = MediaType.APPLICATION_JSON_VALUE)
    void rejectPurchase(@RequestParam int id, @RequestParam int qty);
}


