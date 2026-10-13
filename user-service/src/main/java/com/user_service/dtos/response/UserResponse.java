package com.user_service.dtos.response;

import java.time.LocalDateTime;

public record UserResponse(
        Long id,
        String name,
        String email,
        String mobileNo,
        String address,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
