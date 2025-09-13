package org.example.ecommerce.testdata;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Collections;
import java.util.UUID;

import org.example.ecommerce.model.NewOrder;
import org.example.ecommerce.model.Order;

public class OrderTestData {
    public static final UUID DEFAULT_ORDER_ID = UUID.randomUUID();
    public static final BigDecimal DEFAULT_TOTAL_AMOUNT = BigDecimal.valueOf(10);
    public static final Order.StatusEnum DEFAULT_ORDER_STATUS = Order.StatusEnum.CREATED;

    public static NewOrder createNewOrderModel(UUID customerId, UUID addressId, UUID productId, BigDecimal itemPrice) {
        NewOrder newOrder = new NewOrder();
        newOrder.setCustomerId(customerId.toString());
        newOrder.setAddressId(addressId.toString());
        newOrder.setItems(
            Collections.singletonList(ItemTestData.createItemModel(productId, itemPrice)));

        return newOrder;
    }

    public static Order createOrderModel(UUID orderId, UUID userId) {
        Order order = new Order();
        order.setId(orderId.toString());
        order.setCustomer(UserTestData.createUserModel(userId));
        order.setTotal(DEFAULT_TOTAL_AMOUNT);
        order.setStatus(DEFAULT_ORDER_STATUS);
        order.setDate(OffsetDateTime.now());
        return order;
    }

}