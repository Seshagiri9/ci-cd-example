package com.ecomm.example.controller;

import com.ecomm.example.dto.OrderRequestDTO;
import com.ecomm.example.dto.OrderResponseDTO;
import com.ecomm.example.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderResponseDTO> placeOrder(
            @RequestBody @Valid OrderRequestDTO requestDTO) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(orderService.placeOrder(requestDTO));
    }

    @GetMapping("/history/{userId}")
    public ResponseEntity<List<OrderResponseDTO>> orderHistory(
            @PathVariable Long userId) {

        return ResponseEntity.ok(
                orderService.getOrderHistory(userId)
        );
    }
}

