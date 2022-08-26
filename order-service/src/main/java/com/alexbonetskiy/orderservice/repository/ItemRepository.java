package com.alexbonetskiy.orderservice.repository;

import com.alexbonetskiy.orderservice.domain.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;


@Transactional(readOnly = true)
public interface ItemRepository extends JpaRepository<Item, Integer> {

    Item getByCatalogIdAndNameAndDescriptionAndPrice(Integer id, String name, String description, BigDecimal price);
}
