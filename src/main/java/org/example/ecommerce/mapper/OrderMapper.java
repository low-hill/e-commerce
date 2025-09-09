package org.example.ecommerce.mapper;

import java.sql.Timestamp;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

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
    @Mapping(source = "orderDate", target = "date")
    Order toModel(OrderEntity entity);

    OrderEntity toEntity(Order model);

    default OffsetDateTime map(Timestamp timestamp) {
        if (timestamp == null) {
            return null;
        }
        return timestamp.toInstant().atOffset(ZoneOffset.UTC);
    }

    default Timestamp map(OffsetDateTime offsetDateTime) {
        if (offsetDateTime == null) {
            return null;
        }
        return Timestamp.from(offsetDateTime.toInstant());
    }
}