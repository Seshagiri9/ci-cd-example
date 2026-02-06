package com.ecomm.example.service;

import com.ecomm.example.dto.ProductResponseDTO;
import org.springframework.data.domain.Page;

public interface ProductService {

    Page<ProductResponseDTO> searchProducts(String keyword, int page, int size);

}
