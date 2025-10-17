package com.example.inventory.utils;

import com.example.inventory.dto.response.ItemResponseDto;
import com.example.inventory.enums.ItemCategory;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ItemRedisMapper {

    public static Map<String, String> toHash(ItemResponseDto dto) {

        Map<String,String> map = new HashMap<>();
        map.put("id", dto.getId().toString());
        map.put("name", dto.getName());
        map.put("description", dto.getDescription());
        map.put("sku", dto.getSku());
        map.put("category", dto.getCategory().name());
        map.put("price", dto.getPrice().toString());
        return map;
    }

    public static ItemResponseDto fromHash(Map<Object, Object> hash) {

        return ItemResponseDto.builder()
                .id(UUID.fromString((String) hash.get("id")))
                .name((String) hash.get("name"))
                .description((String) hash.get("description"))
                .sku((String) hash.get("sku"))
                .category(ItemCategory.valueOf((String) hash.get("category")))
                .price(new BigDecimal((String) hash.get("price")))
                .build();

    }
}
