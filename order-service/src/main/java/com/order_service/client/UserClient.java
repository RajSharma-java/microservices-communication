package com.order_service.client;

import com.order_service.dtos.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClient;
import org.springframework.web.server.ResponseStatusException;

@Component
@RequiredArgsConstructor
public class UserClient {

    private final RestClient userRestClient;

    public UserDto getUserById(Long id) {
        try {
            return userRestClient.get()
                    .uri("/api/users?id={id}", id)
                    .retrieve()
                    .body(UserDto.class);
        } catch (HttpClientErrorException.NotFound e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "User not found with id: " + id);
        } catch (ResourceAccessException e) {
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE,
                    "user-service is unavailable, try again later");
        }
    }
}