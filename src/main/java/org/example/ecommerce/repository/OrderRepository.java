package org.example.ecommerce.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.example.ecommerce.entity.OrderEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<OrderEntity, UUID> {
    @Query("select o from OrderEntity o where o.userEntity.id = :customerId")
    Optional<List<OrderEntity>> findByCustomerId(UUID customerId);
}
