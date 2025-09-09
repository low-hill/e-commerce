package org.example.ecommerce.mapper;

import java.sql.Timestamp;
import java.time.LocalDate;

import org.example.ecommerce.entity.ShipmentEntity;
import org.example.ecommerce.model.Shipment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ShipmentMapper {
    @Mapping(source = "id", target = "id")
    @Mapping(source = "estDeliveryDate", target = "estDeliveryDate")
    @Mapping(source = "carrier", target = "carrier")
    Shipment toModel(ShipmentEntity entity);

    ShipmentEntity toEntity(Shipment model);

    default Timestamp map(LocalDate date) {
        if (date == null) {
            return null;
        }
        return Timestamp.valueOf(date.atStartOfDay());
    }

    default LocalDate map(Timestamp timestamp) {
        if (timestamp == null) {
            return null;
        }
        return timestamp.toLocalDateTime().toLocalDate();
    }
}