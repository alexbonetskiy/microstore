package com.alexbonetskiy.itemservice.utils.mapstruct;


import com.alexbonetskiy.itemservice.domain.Item;
import com.alexbonetskiy.itemservice.dto.ItemTO;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;


@org.mapstruct.Mapper
public interface ItemMapper {

    ItemMapper ITEM_MAPPER = Mappers.getMapper(ItemMapper.class);


    Item item(ItemTO itemTO);

    @Mapping(target = "exception", ignore = true)
    ItemTO itemTO(Item item);
}
