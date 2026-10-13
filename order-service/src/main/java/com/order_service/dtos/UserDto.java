package com.order_service.dtos;

import java.time.LocalDateTime;

public record UserDto(
        Long id,
        String name,
        String email,
        String mobileNo,
        String address,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}