package com.alexbonetskiy.orderservice.client;

import com.alexbonetskiy.orderservice.dto.ItemTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient(name = "item-service")
public interface ItemServiceClient {

    @GetMapping(value = "/items/basket", produces = MediaType.APPLICATION_JSON_VALUE)
    ItemTO getItemToOrder(@RequestParam int id, @RequestParam int qty);

    @PostMapping(value = "/items/basket/confirm")
    ItemTO reserveItem(@RequestParam int id, @RequestParam int qty);

    @PostMapping(value = "/items/basket/reject")
    void rejectPurchase(@RequestParam int id, @RequestParam int qty);
}


