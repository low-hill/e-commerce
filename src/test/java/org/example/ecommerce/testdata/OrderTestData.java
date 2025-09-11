package org.example.ecommerce.testdata;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Collections;
import java.util.UUID;

import org.example.ecommerce.entity.OrderEntity;
import org.example.ecommerce.entity.UserEntity;
import org.example.ecommerce.model.NewOrder;
import org.example.ecommerce.model.Order;

public class OrderTestData {
    public static final UUID DEFAULT_ORDER_ID = UUID.randomUUID();
    public static final BigDecimal DEFAULT_TOTAL_AMOUNT = BigDecimal.valueOf(10);
    public static final Order.StatusEnum DEFAULT_ORDER_STATUS = Order.StatusEnum.CREATED;

//    public static Order createNewOrderModel() {
//        return createNewOrderModel(DEFAULT_ORDER_ID);
//    }

    public static NewOrder createNewOrderModel(){
        NewOrder newOrder = new NewOrder();
        newOrder.setCustomerId(DEFAULT_ORDER_ID.toString());
        newOrder.setAddressId(DEFAULT_ORDER_ID.toString());
        newOrder.setItems(Collections.singletonList(ItemTestData.createItemModel(ProductTestData.DEFAULT_PRODUCT_ID)));

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

    public static OrderEntity createOrderEntity() {
        return OrderEntity.builder()
                .id(DEFAULT_ORDER_ID)
                .userEntity(UserTestData.createUserEntity())
                .totalAmount(DEFAULT_TOTAL_AMOUNT)
                .status(Order.StatusEnum.CREATED)
                .orderDate(OffsetDateTime.now())
                .build();
    }

    public static OrderEntity.OrderEntityBuilder orderBuilder(UUID orderId, UserEntity user) {
        return OrderEntity.builder()
                .id(orderId)
                .userEntity(user)
                .totalAmount(DEFAULT_TOTAL_AMOUNT)
                .status(Order.StatusEnum.CREATED)
                .orderDate(OffsetDateTime.now());
    }
}