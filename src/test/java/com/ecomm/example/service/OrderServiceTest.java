package com.ecomm.example.service;

import com.ecomm.example.dto.OrderItemDTO;
import com.ecomm.example.dto.OrderRequestDTO;
import com.ecomm.example.dto.OrderResponseDTO;
import com.ecomm.example.entity.Product;
import com.ecomm.example.repository.OrderRepository;
import com.ecomm.example.repository.ProductRepository;
import com.ecomm.example.serviceImpl.OrderServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private OrderServiceImpl orderService;

    @Test
    void shouldPlaceOrderSuccessfully() {
        // 1. Setup Data
        Product product = new Product();
        product.setId(1L);
        product.setPrice(100.0);

        // 2. Setup Mocks
        Mockito.when(productRepository.findById(1L))
                .thenReturn(Optional.of(product));

        // ADD THIS LINE:
        // This ensures that when save() is called, it returns a non-null Order object
        Mockito.when(orderRepository.save(Mockito.any()))
                .thenAnswer(invocation -> {
                    com.ecomm.example.entity.Order savedOrder = invocation.getArgument(0);
                    savedOrder.setId(101L); // Give it a dummy ID
                    return savedOrder;
                });

        // 3. Define Request
        OrderRequestDTO request = new OrderRequestDTO();
        request.setUserId(1L);
        OrderItemDTO item = new OrderItemDTO();
        item.setProductId(1L);
        item.setQuantity(2);
        request.setItems(List.of(item));

        // 4. Execute
        OrderResponseDTO response = orderService.placeOrder(request);

        // 5. Assert
        Assertions.assertNotNull(response);
        Assertions.assertEquals(200.0, response.getTotalAmount());
    }
}