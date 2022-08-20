package com.alexbonetskiy.itemservice.repository;

import com.alexbonetskiy.itemservice.domain.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface ItemRepository extends JpaRepository<Item, Integer> {
}
