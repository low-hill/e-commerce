package org.example.ecommerce.repository;

import java.util.UUID;

import org.example.ecommerce.entity.OrderEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import org.springframework.data.jpa.repository.Query;

public interface OrderRepository extends CrudRepository<OrderEntity, UUID> {
    @Query("select o from OrderEntity o where o.userEntity.id = :customerId")
    List<OrderEntity> findByCustomerId(UUID customerId);
}
