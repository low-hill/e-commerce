package org.example.ecommerce.mapper;

import org.example.ecommerce.entity.AddressEntity;
import org.example.ecommerce.model.Address;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AddressMapper {
    @Mapping(source = "id", target = "id")
    @Mapping(source = "number", target = "number")
    @Mapping(source = "residency", target = "residency")
    @Mapping(source = "street", target = "street")
    @Mapping(source = "city", target = "city")
    @Mapping(source = "state", target = "state")
    @Mapping(source = "country", target = "country")
    @Mapping(source = "pincode", target = "pincode")
    Address toModel(AddressEntity entity);

    AddressEntity toEntity(Address model);
}