package com.order_service.service.impl;

import com.order_service.client.UserClient;
import com.order_service.dtos.OrderRequest;
import com.order_service.dtos.OrderResponse;
import com.order_service.dtos.UserDto;
import com.order_service.entity.Order;
import com.order_service.repository.OrderRepository;
import com.order_service.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final UserClient userClient;
    @Override
    public OrderResponse createOrder(OrderRequest request) {
        validate(request);

        UserDto user = userClient.getUserById(request.userId());

        Order order = new Order();
        applyRequest(order, request);
        order.setStatus("CREATED");

        return toResponse(orderRepository.save(order), user);
    }
    @Override
    public OrderResponse getOrderById(Long id) {
        Order order = findOrder(id);
        UserDto user = userClient.getUserById(order.getUserId());
        return toResponse(order, user);
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderResponse> getAllOrders() {
        return orderRepository.findAll().stream()
                .map(order -> toResponse(order, null))
                .toList();
    }

    @Override
    public List<OrderResponse> getOrdersByUserId(Long userId) {
        UserDto user = userClient.getUserById(userId);
        return orderRepository.findByUserId(userId).stream()
                .map(order -> toResponse(order, user))
                .toList();
    }
    @Override
    public OrderResponse updateOrder(Long id, OrderRequest request) {
        validate(request);

        Order order = findOrder(id);

        UserDto user = userClient.getUserById(request.userId()); // user-service ko call

        applyRequest(order, request);

        return toResponse(orderRepository.save(order), user);
    }

    @Override
    @Transactional
    public void deleteOrder(Long id) {
        orderRepository.delete(findOrder(id));
    }

    // ---------- helpers ----------

    private Order findOrder(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Order not found with id: " + id));
    }

    private void validate(OrderRequest request) {
        if (request.userId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "userId is required");
        }
        if (request.quantity() == null || request.quantity() <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "quantity must be greater than 0");
        }
        if (request.price() == null || request.price().signum() < 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "price must not be negative");
        }
    }

    private void applyRequest(Order order, OrderRequest request) {
        order.setUserId(request.userId());
        order.setProductName(request.productName());
        order.setQuantity(request.quantity());
        order.setPrice(request.price());
    }
    private OrderResponse toResponse(Order order, UserDto user) {
        return new OrderResponse(
                order.getId(), order.getUserId(), order.getProductName(),
                order.getQuantity(), order.getPrice(), order.getStatus(),
                order.getCreatedAt(), order.getUpdatedAt(), user
        );
    }
}
