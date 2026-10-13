package com.order_service.dtos;

import java.math.BigDecimal;

public record OrderRequest(
        Long userId,
        String productName,
        Integer quantity,
        BigDecimal price
) {
}
