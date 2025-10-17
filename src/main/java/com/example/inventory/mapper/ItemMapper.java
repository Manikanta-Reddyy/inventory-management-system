package com.example.inventory.mapper;

import com.example.inventory.dto.request.ItemRequestDto;
import com.example.inventory.dto.response.ItemResponseDto;
import com.example.inventory.model.Item;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ItemMapper {

    Item toEntity(ItemRequestDto itemRequestDto);

    ItemResponseDto toDto(Item item);

}
