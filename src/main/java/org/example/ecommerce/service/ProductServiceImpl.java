package org.example.ecommerce.service;

import java.util.Optional;
import java.util.UUID;

import lombok.RequiredArgsConstructor;
import org.example.ecommerce.entity.ProductEntity;
import org.example.ecommerce.repository.ProductRepository;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ProductServiceImpl implements ProductService{
    private final ProductRepository productRepository;

    @Override
    public Iterable<ProductEntity> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Optional<ProductEntity> getProduct(String id) {
        return productRepository.findById(UUID.fromString(id));
    }

}
