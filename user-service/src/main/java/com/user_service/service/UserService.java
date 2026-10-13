package com.user_service.service;

import java.util.List;
import com.user_service.dtos.request.UserRequest;
import com.user_service.dtos.response.UserResponse;

public interface UserService {

    UserResponse createUser(UserRequest request);

    UserResponse getUserById(Long id);

    List<UserResponse> getAllUsers();

    UserResponse updateUser(Long id, UserRequest request);

    void deleteUser(Long id);
}