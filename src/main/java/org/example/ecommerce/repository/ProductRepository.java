package org.example.ecommerce.repository;

import java.util.List;
import java.util.UUID;
import jakarta.persistence.LockModeType;

import org.example.ecommerce.entity.ProductEntity;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<ProductEntity, UUID> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select p from ProductEntity p where p.id in :ids")
    List<ProductEntity> findByIdInWithLock(List<UUID> ids);
}
