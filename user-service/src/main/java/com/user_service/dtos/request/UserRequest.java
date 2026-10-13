package com.user_service.dtos.request;

public record UserRequest(
        String name,
        String email,
        String mobileNo,
        String address
) {
}
