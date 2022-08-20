package com.alexbonetskiy.itemservice.service;

import com.alexbonetskiy.itemservice.domain.Item;
import com.alexbonetskiy.itemservice.dto.ItemTO;
import com.alexbonetskiy.itemservice.exception.OutOfStockException;
import com.alexbonetskiy.itemservice.repository.ItemRepository;
import com.alexbonetskiy.itemservice.utils.ItemMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class ItemService {


    private final ItemRepository itemRepository;


    public Optional<Item> getItemById(int id) {
        return itemRepository.findById(id);
    }

    public List<Item> getAllItems() {
        log.info("getAllItems");
        return itemRepository.findAll();
    }

    @Transactional
    public ItemTO addToOrder(int id, int qty) {
        log.info("addToOrder item id={}, quantity={}", id, qty);
        Optional<Item> optionalItem = itemRepository.findById(id);
        if (optionalItem.isPresent()) {
            if (optionalItem.get().isAvailable(qty)) {
                Item item = optionalItem.get();
                item.setQuantity(item.getQuantity() - qty);
                return ItemMapper.ITEM_MAPPER.itemTO(item);
            } else throw new OutOfStockException("Item is out of stock");
        } else throw new EntityNotFoundException("Item id is invalid");
    }

    public Item save(Item item) {
        log.info("save item {}", item);
        return itemRepository.save(item);
    }
}
