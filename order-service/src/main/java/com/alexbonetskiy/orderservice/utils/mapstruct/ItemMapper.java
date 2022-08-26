package com.alexbonetskiy.orderservice.utils.mapstruct;



import com.alexbonetskiy.orderservice.domain.Item;
import com.alexbonetskiy.orderservice.dto.ItemTO;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;


@org.mapstruct.Mapper
public interface ItemMapper {

    ItemMapper ITEM_MAPPER = Mappers.getMapper(ItemMapper.class);



    @Mapping(target = "catalogId", source = "id")
    @Mapping(target = "id", ignore = true)
    Item item(ItemTO itemTO);

    @Mapping(target = "exception", ignore = true)
    @Mapping(target = "id", source = "catalogId")
    ItemTO itemTO(Item item);
}
