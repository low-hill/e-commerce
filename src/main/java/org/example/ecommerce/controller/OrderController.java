package org.example.ecommerce.controller;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.example.ecommerce.api.OrderApi;
import org.example.ecommerce.hateoas.OrderRepresentationModelAssembler;
import org.example.ecommerce.model.NewOrder;
import org.example.ecommerce.model.Order;
import org.example.ecommerce.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.ResponseEntity.notFound;

@RestController
@RequiredArgsConstructor
public class OrderController implements OrderApi {

    private final OrderService orderService;
    private final OrderRepresentationModelAssembler orderPresentationModelAssembler;

    @Override
    public ResponseEntity<Order> addOrder(NewOrder newOrder) {
        return orderService.addOrder(newOrder)
                .map(orderPresentationModelAssembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(notFound().build());
    }

    @Override
    public ResponseEntity<Order> getByOrderId(String orderId) {
        return orderService.getByOrderId(orderId)
                .map(orderPresentationModelAssembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(notFound().build());
    }

    @Override
    public ResponseEntity<List<Order>> getOrdersByCustomerId(String customerId) {
        return orderService.getOrdersByCustomerId(customerId)
                .map(orderPresentationModelAssembler::toListModel)
                .map(ResponseEntity::ok)
                .orElse(notFound().build());
    }
}
