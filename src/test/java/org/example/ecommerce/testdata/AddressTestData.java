package org.example.ecommerce.testdata;

import java.util.UUID;
import org.example.ecommerce.entity.AddressEntity;

public class AddressTestData {
    public static final UUID DEFAULT_ADDRESS_ID = UUID.randomUUID();

    public static AddressEntity createAddressEntity() {
        return AddressEntity.builder()
                .id(DEFAULT_ADDRESS_ID)
                .street("123 Main St")
                .city("Anytown")
                .state("CA")
                .country("USA")
                .pincode("12345")
                .build();
    }
}
