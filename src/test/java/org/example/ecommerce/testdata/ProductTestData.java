package org.example.ecommerce.testdata;

import java.math.BigDecimal;
import java.util.UUID;

import org.example.ecommerce.entity.ProductEntity;

public class ProductTestData {
    public static final UUID DEFAULT_PRODUCT_ID = UUID.fromString("6d62d909-f957-430e-8689-b5129c0bb75e");
    public static final String DEFAULT_PRODUCT_NAME = "Test Product";
    public static final String DEFAULT_DESCRIPTION = "Test Product Description";
    public static final BigDecimal DEFAULT_PRICE = BigDecimal.valueOf(100);
    public static final int DEFAULT_COUNT = 10;

    public static ProductEntity createProductEntity() {
        return createProductEntity(DEFAULT_PRODUCT_ID, DEFAULT_PRODUCT_NAME, DEFAULT_DESCRIPTION, DEFAULT_PRICE, DEFAULT_COUNT);
    }

    public static ProductEntity createProductEntity(int count) {
        return createProductEntity(DEFAULT_PRODUCT_ID, DEFAULT_PRODUCT_NAME, DEFAULT_DESCRIPTION, DEFAULT_PRICE, count);
    }

    public static ProductEntity createProductEntity(UUID productId, String name, String description, BigDecimal price, int count) {
        return ProductEntity.builder()
                .id(productId)
                .name(name)
                .description(description)
                .price(price)
                .count(count)
                .build();
    }
}