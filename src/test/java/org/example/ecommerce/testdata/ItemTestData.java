package org.example.ecommerce.testdata;

import java.math.BigDecimal;
import java.util.UUID;

import org.example.ecommerce.model.Item;

public class ItemTestData {
    public static final UUID DEFAULT_PRODUCT_ID = UUID.randomUUID();
    public static final BigDecimal DEFAULT_PRICE = BigDecimal.valueOf(100);
    public static final int DEFAULT_COUNT = 1;


    public static Item createItemModel(UUID productId, BigDecimal price) {
        return createItemModel(productId, price, DEFAULT_COUNT);
    }

    public static Item createItemModel(UUID productId, BigDecimal price, int count) {
        Item item = new Item();
        item.setId(productId.toString());
        item.setQuantity(count);
        item.setUnitPrice(price);
        return item;
    }
}