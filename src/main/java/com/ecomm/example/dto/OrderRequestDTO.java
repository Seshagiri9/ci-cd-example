package com.ecomm.example.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
public class OrderRequestDTO {
    private Long userId;
    private LocalDateTime orderDate;
    private Double totalAmount;
    private List<OrderItemDTO> items;

}
