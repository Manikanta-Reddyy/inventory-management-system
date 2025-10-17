package com.example.inventory.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
public class ErrorResponseDto {

    private String message;

    private Map<String, String> error;
}
