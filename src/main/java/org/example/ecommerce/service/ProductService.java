package org.example.ecommerce.service;

import java.util.Optional;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import org.example.ecommerce.entity.ProductEntity;

public interface ProductService {
    @NotNull
    Iterable<ProductEntity> getAllProducts();
    Optional<ProductEntity> getProduct(@Min(value = 1L, message = "Invalid product ID.") String id);
}
