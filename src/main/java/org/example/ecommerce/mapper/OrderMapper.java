package org.example.ecommerce.mapper;

import org.example.ecommerce.entity.OrderEntity;
import org.example.ecommerce.model.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {UserMapper.class, AddressMapper.class, ItemMapper.class, ShipmentMapper.class})
public interface OrderMapper {
    @Mapping(source = "id", target = "id")
    @Mapping(source = "userEntity", target = "customer")
    @Mapping(source = "addressEntity", target = "address")
    @Mapping(source = "items", target = "items")
    @Mapping(source = "totalAmount", target = "total")
    @Mapping(source = "status", target = "status")
    @Mapping(source = "shipment", target = "shipment")
    @Mapping(source = "orderDate", target = "date")
    Order toModel(OrderEntity entity);

    OrderEntity toEntity(Order model);
}