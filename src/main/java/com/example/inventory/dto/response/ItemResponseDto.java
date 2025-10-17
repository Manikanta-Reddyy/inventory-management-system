package com.example.inventory.dto.response;

import com.example.inventory.enums.ItemCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemResponseDto {

    private UUID id;

    private String name;

    private String description;

    private String sku;

    private BigDecimal price;

    private ItemCategory category;
}
