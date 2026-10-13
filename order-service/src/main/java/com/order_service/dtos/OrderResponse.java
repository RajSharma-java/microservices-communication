package com.order_service.dtos;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record OrderResponse(
        Long id,
        Long userId,
        String productName,
        Integer quantity,
        BigDecimal price,
        String status,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        UserDto user
) {
}
