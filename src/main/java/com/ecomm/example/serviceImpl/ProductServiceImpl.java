package com.ecomm.example.serviceImpl;

import com.ecomm.example.dto.ProductResponseDTO;
import com.ecomm.example.entity.Product;
import com.ecomm.example.repository.ProductRepository;
import com.ecomm.example.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public Page<ProductResponseDTO> searchProducts(String keyword, int page, int size) {

        Pageable pageable = PageRequest.of(page, size, Sort.by("name").ascending());

        return productRepository
                .findByNameContainingIgnoreCase(keyword, pageable)
                .map(this::mapToDTO);
    }

    private ProductResponseDTO mapToDTO(Product product) {
        ProductResponseDTO dto = new ProductResponseDTO();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setPrice(product.getPrice());
        return dto;
    }
}

