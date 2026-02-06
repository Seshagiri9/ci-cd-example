package com.ecomm.example.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class OrderItemDTO {
    private Long productId;
    private Integer quantity;
    private Double price;

}
