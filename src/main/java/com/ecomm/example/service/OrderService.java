package com.ecomm.example.service;

import com.ecomm.example.dto.OrderRequestDTO;
import com.ecomm.example.dto.OrderResponseDTO;

import java.util.List;

public interface OrderService {
    OrderResponseDTO placeOrder(OrderRequestDTO requestDTO);
    List<OrderResponseDTO> getOrderHistory(Long userId);
}

