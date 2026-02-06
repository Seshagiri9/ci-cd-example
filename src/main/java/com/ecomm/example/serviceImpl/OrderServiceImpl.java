package com.ecomm.example.serviceImpl;

import com.ecomm.example.dto.OrderItemDTO;
import com.ecomm.example.dto.OrderRequestDTO;
import com.ecomm.example.dto.OrderResponseDTO;
import com.ecomm.example.entity.Order;
import com.ecomm.example.entity.OrderItem;
import com.ecomm.example.entity.Product;
import com.ecomm.example.repository.OrderRepository;
import com.ecomm.example.repository.ProductRepository;
import com.ecomm.example.service.OrderService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;


    @Override
    @Transactional
    public OrderResponseDTO placeOrder(OrderRequestDTO requestDTO) {
        Order order = new Order();
        order.setUserId(requestDTO.getUserId());
        order.setOrderDate(LocalDateTime.now());

        List<OrderItem> items = new ArrayList<>();
        double total = 0;

        for (OrderItemDTO dto : requestDTO.getItems()) {
            Product product = productRepository.findById(dto.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product ID " + dto.getProductId() + " not found"));

            OrderItem item = new OrderItem();
            item.setProductId(product.getId());
            item.setQuantity(dto.getQuantity());
            item.setPrice(product.getPrice());

            // Link the item to the order
            item.setOrder(order);

            total += product.getPrice() * dto.getQuantity();
            items.add(item);
        }

        order.setItems(items);
        order.setTotalAmount(total);

        // Because of CascadeType.ALL, saving the order saves all items too
        Order savedOrder = orderRepository.save(order);

        return mapToResponse(savedOrder);
    }

    @Override
    public List<OrderResponseDTO> getOrderHistory(Long userId) {

        LocalDateTime lastSixMonths = LocalDateTime.now().minusMonths(6);

        return orderRepository
                .findByUserIdAndOrderDateAfter(userId, lastSixMonths)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    private OrderResponseDTO mapToResponse(Order order) {
        OrderResponseDTO dto = new OrderResponseDTO();
        dto.setOrderId(order.getId());
        dto.setTotalAmount(order.getTotalAmount());
        dto.setOrderDate(order.getOrderDate());
        return dto;
    }
}
