package com.ecomm.example.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class OrderResponseDTO {

    private Long orderId;
    private Double totalAmount;
    private LocalDateTime orderDate;

}
