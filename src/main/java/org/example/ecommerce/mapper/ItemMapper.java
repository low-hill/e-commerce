package org.example.ecommerce.mapper;

import org.example.ecommerce.entity.OrderItemEntity;
import org.example.ecommerce.model.Item;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ItemMapper {
    @Mapping(source = "productEntity.id", target = "id")
    @Mapping(source = "quantity", target = "quantity")
    @Mapping(source = "unitPrice", target = "unitPrice")
    Item toModel(OrderItemEntity entity);

    OrderItemEntity toEntity(Item model);
}