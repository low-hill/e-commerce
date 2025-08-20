package org.example.ecommerce.repository;

import java.util.Optional;
import java.util.UUID;

import org.example.ecommerce.entity.CartEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface CartRepository extends CrudRepository<CartEntity, UUID> {
    @Query("SELECT c FROM CartEntity c LEFT JOIN FETCH c.items i LEFT JOIN FETCH i.product WHERE c.userId = :customerId")
    Optional<CartEntity> findByCustomerIdWithItems(UUID customerId);

}
