package org.example.ecommerce.repository;

import java.util.UUID;

import org.example.ecommerce.entity.ProductEntity;
import org.springframework.data.repository.CrudRepository;


public interface ProductRepository extends CrudRepository<ProductEntity, UUID> {
}
