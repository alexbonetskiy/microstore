package com.alexbonetskiy.itemservice.repository;

import com.alexbonetskiy.itemservice.domain.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import static com.alexbonetskiy.itemservice.utils.ValidationUtil.checkModification;

@Transactional(readOnly = true)
public interface ItemRepository extends JpaRepository<Item, Integer> {

    @Modifying
    @Query("DELETE FROM Item i WHERE i.id=:id")
    int delete(int id);

}
