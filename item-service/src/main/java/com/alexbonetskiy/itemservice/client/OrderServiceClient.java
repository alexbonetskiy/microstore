package com.alexbonetskiy.itemservice.client;


import com.alexbonetskiy.itemservice.dto.ItemTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "order-service")
public interface OrderServiceClient {


    @PostMapping(value = "/orders/current", consumes = MediaType.APPLICATION_JSON_VALUE)
    void updateItemInCurrentOrders(@RequestBody ItemTO itemTO);

    @DeleteMapping("/orders/current/basket/{id}")
    void deleteItemFromCurrentOrders(@PathVariable int id);
}
