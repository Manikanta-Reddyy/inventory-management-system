package com.example.inventory.mapper;

import com.example.inventory.dto.response.InventoryStatusDto;
import com.example.inventory.model.InventoryCounter;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface InventoryMapper {

    @Mapping(source = "item.id", target = "itemId")
    @Mapping(source = "item.name", target = "itemName")
    InventoryStatusDto toDto(InventoryCounter inventoryCounter);
}
