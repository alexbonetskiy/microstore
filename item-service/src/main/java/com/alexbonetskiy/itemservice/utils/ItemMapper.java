package com.alexbonetskiy.itemservice.utils;


import com.alexbonetskiy.itemservice.domain.Item;
import com.alexbonetskiy.itemservice.dto.ItemTO;
import org.mapstruct.factory.Mappers;


@org.mapstruct.Mapper
public interface ItemMapper {

    ItemMapper ITEM_MAPPER = Mappers.getMapper(ItemMapper.class);

    Item item(ItemTO itemTO);
    ItemTO itemTO(Item item);
}
