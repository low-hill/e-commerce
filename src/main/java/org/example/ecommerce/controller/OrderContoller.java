package org.example.ecommerce.controller;

import lombok.RequiredArgsConstructor;
import org.example.ecommerce.api.OrderApi;
import org.example.ecommerce.model.NewOrder;
import org.example.ecommerce.model.Order;
import org.example.ecommerce.service.OrderService;
import org.springframework.http.ResponseEntity;

import static org.springframework.http.ResponseEntity.notFound;

@RequiredArgsConstructor
public class OrderContoller implements OrderApi {

    private final OrderService orderService;

    @Override
    public ResponseEntity<Order> addOrder(NewOrder newOrder) {
        return orderService.addOrder(newOrder)
                .map(ResponseEntity::ok)
                .orElse(notFound().build());
    }

    @Override
    public ResponseEntity<Order> getByOrderId(String orderId) {
        return orderService.getByOrderId(orderId).map(ResponseEntity::ok).orElse(notFound().build());
    }
}
