package org.example.ecommerce.repository;

import java.util.UUID;

import org.example.ecommerce.entity.AddressEntity;
import org.springframework.data.repository.CrudRepository;

public interface AddressRepository extends CrudRepository<AddressEntity, UUID> {
}
