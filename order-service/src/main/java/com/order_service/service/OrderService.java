package com.order_service.service;

import com.order_service.dtos.OrderRequest;
import com.order_service.dtos.OrderResponse;

import java.util.List;

public interface OrderService {

    OrderResponse createOrder(OrderRequest request);

    OrderResponse getOrderById(Long id);

    List<OrderResponse> getAllOrders();

    List<OrderResponse> getOrdersByUserId(Long userId);

    OrderResponse updateOrder(Long id, OrderRequest request);

    void deleteOrder(Long id);
}
