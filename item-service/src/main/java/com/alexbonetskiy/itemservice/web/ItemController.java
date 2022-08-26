package com.alexbonetskiy.itemservice.web;

import com.alexbonetskiy.itemservice.domain.Item;
import com.alexbonetskiy.itemservice.dto.ItemTO;
import com.alexbonetskiy.itemservice.service.ItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

import static com.alexbonetskiy.itemservice.utils.ValidationUtil.assureIdConsistent;
import static com.alexbonetskiy.itemservice.utils.ValidationUtil.checkNew;

@RestController
@RequestMapping(value = ItemController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@RequiredArgsConstructor
public class ItemController {

    public static final String REST_URL = "/items";

    private final ItemService itemService;

    @GetMapping
    public List<Item> getAllItems() {
        log.info("getAllItems");
        return itemService.getAllItems();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Item> getItemById(@PathVariable int id) {
        return ResponseEntity.of(itemService.getItemById(id));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Item> createWithLocation(@Valid @RequestBody Item item) {
        log.info("create {}", item);
        checkNew(item);
        Item created = itemService.save(item);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@Valid @RequestBody Item item, @PathVariable int id) {
        log.info("update {}", id);
        assureIdConsistent(item, id);
        itemService.update(item);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteItem(@PathVariable int id) {
        log.info("delete item {}", id);
        itemService.deleteItem(id);
    }

    @GetMapping("/basket")
    public ItemTO addItemToOrder(@RequestParam int id, @RequestParam int qty) {
        log.info("add item {} with quantity {} to order", id, qty);
        return itemService.addItemToOrder(id, qty);
    }

    @PostMapping(value = "/basket/confirm", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ItemTO reserveItem(@RequestParam int id, @RequestParam int qty) {
        log.info("reserve item {} with quantity {}", id, qty);
        return itemService.reserveItem(id, qty);
    }

    @PostMapping(value = "/items/basket/reject", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void rejectPurchase(@RequestParam int id, @RequestParam int qty) {
        log.info("reject item {} with quantity {}", id, qty);
        itemService.rejectPurchase(id, qty);
    }


}
