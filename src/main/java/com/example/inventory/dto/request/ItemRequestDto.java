package com.example.inventory.dto.request;

import com.example.inventory.enums.ItemCategory;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemRequestDto {

    @NotBlank(message = "name is required")
    private String name;

    @NotBlank(message = "description is required")
    private String description;

    @NotBlank(message = "sku is required")
    private String sku;

    @NotNull(message = "item category is required")
    private ItemCategory category;

    @NotNull(message = "price is required")
    private BigDecimal price;
}
