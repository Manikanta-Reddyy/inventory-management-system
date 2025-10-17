package com.example.inventory.mapper;

import com.example.inventory.dto.request.SupplyRequestDto;
import com.example.inventory.dto.response.SupplyResponseDto;
import com.example.inventory.model.Supply;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SupplyMapper {

    @Mapping(source = "itemId", target = "item.id")
    Supply toEntity(SupplyRequestDto supplyRequestDto);

    @Mapping(source = "item.id", target = "itemId")
    @Mapping(source = "item.name", target = "itemName")
    SupplyResponseDto toDto(Supply supply);

}
