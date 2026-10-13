package com.order_service.controller;

import com.order_service.dtos.OrderRequest;
import com.order_service.dtos.OrderResponse;
import com.order_service.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
@Tag(name = "Orders", description = "Order management APIs")
public class OrderController {

    private final OrderService orderService;

    @Operation(summary = "Create an order")
    @ApiResponse(responseCode = "201", description = "Order created")
    @ApiResponse(responseCode = "400", description = "Invalid input")
    @PostMapping
    public ResponseEntity<OrderResponse> createOrder(@RequestBody OrderRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.createOrder(request));
    }

    @Operation(summary = "Get an order by id")
    @ApiResponse(responseCode = "404", description = "Order not found")
    @GetMapping()
    public ResponseEntity<OrderResponse> getOrderById(@RequestParam Long id) {
        return ResponseEntity.ok(orderService.getOrderById(id));
    }

    @Operation(summary = "Get all orders")
    @GetMapping("all-orders")
    public ResponseEntity<List<OrderResponse>> getAllOrders() {
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    @Operation(summary = "Get all orders of a user (by userId)")
    @GetMapping("/user")
    public ResponseEntity<List<OrderResponse>> getOrdersByUser(@RequestParam Long userId) {
        return ResponseEntity.ok(orderService.getOrdersByUserId(userId));
    }

    @Operation(summary = "Update an order")
    @ApiResponse(responseCode = "404", description = "Order not found")
    @PutMapping("update")
    public ResponseEntity<OrderResponse> updateOrder(@RequestParam Long id,
                                                     @RequestBody OrderRequest request) {
        return ResponseEntity.ok(orderService.updateOrder(id, request));
    }

    @Operation(summary = "Delete an order")
    @ApiResponse(responseCode = "204", description = "Order deleted")
    @ApiResponse(responseCode = "404", description = "Order not found")
    @DeleteMapping("delete")
    public ResponseEntity<Void> deleteOrder(@RequestParam Long id) {
        orderService.deleteOrder(id);
        return ResponseEntity.noContent().build();
    }
}
