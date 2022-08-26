package com.alexbonetskiy.itemservice.service;

import com.alexbonetskiy.itemservice.client.OrderServiceClient;
import com.alexbonetskiy.itemservice.domain.Item;
import com.alexbonetskiy.itemservice.dto.ItemTO;
import com.alexbonetskiy.itemservice.repository.ItemRepository;
import com.alexbonetskiy.itemservice.utils.mapstruct.ItemMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.alexbonetskiy.itemservice.utils.ValidationUtil.checkModification;

@Service
@Slf4j
@RequiredArgsConstructor
public class ItemService {


    private final ItemRepository itemRepository;

    private final OrderServiceClient orderServiceClient;


    public Optional<Item> getItemById(int id) {
        return itemRepository.findById(id);
    }

    public List<Item> getAllItems() {
        log.info("getAllItems");
        return itemRepository.findAll();
    }

    public Item save(Item item) {
        log.info("save item {}", item);
        return itemRepository.save(item);
    }

    public void deleteItem(int id) {
        log.info("delete item {}", id);
        checkModification(itemRepository.delete(id), id);
        orderServiceClient.deleteItemFromCurrentOrders(id);
    }

    @Transactional
    public ItemTO reserveItem(int id, int qty) {
        log.info("reserve item id={}, quantity={}", id, qty);
        Optional<Item> optionalItem = itemRepository.findById(id);
        if (optionalItem.isPresent()) {
            if (optionalItem.get().isAvailable(qty)) {
                Item item = optionalItem.get();
                item.setQuantity(item.getQuantity() - qty);
                return ItemMapper.ITEM_MAPPER.itemTO(item);
            } else return new ItemTO("Item is out of stock");
        } else return new ItemTO("Item id is invalid or no longer available");
    }


    public ItemTO addItemToOrder(int id, int qty) {
        log.info("addToOrder item id={}, quantity={}", id, qty);
        Optional<Item> optionalItem = itemRepository.findById(id);
        if (optionalItem.isPresent()) {
            if (optionalItem.get().isAvailable(qty)) {
                Item item = optionalItem.get();
                return ItemMapper.ITEM_MAPPER.itemTO(item);
            } else return new ItemTO("Item is out of stock");
        } else return new ItemTO("Item id is invalid or no longer available");
    }

    @Transactional
    public void rejectPurchase(int id, int qty) {
        log.info("reject item {} with quantity {}", id, qty);
        Optional<Item> optionalItem = itemRepository.findById(id);
        Item item = optionalItem.get();
        item.setQuantity(item.getQuantity() + qty);
    }


    public void update(Item item) {
        log.info("update item id={}", item.getId());
        Item replacedItem = itemRepository.findById(item.getId()).get();
        System.out.println(item);
        System.out.println(replacedItem);
        System.out.println(!item.equals(replacedItem));
        if (item.equals(replacedItem) && item.getQuantity() == replacedItem.getQuantity()){
            return;
        }
        if (!item.equals(replacedItem)) {
            orderServiceClient.updateItemInCurrentOrders(ItemMapper.ITEM_MAPPER.itemTO(item));}
            save(item);

    }
}
