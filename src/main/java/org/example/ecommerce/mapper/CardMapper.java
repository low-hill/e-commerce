package org.example.ecommerce.mapper;

import org.example.ecommerce.entity.CardEntity;
import org.example.ecommerce.model.Card;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CardMapper {
    Card toModel(CardEntity entity);

    @Mapping(target = "user", ignore = true)
    @Mapping(target = "orders", ignore = true)
    CardEntity toEntity(Card model);
}
