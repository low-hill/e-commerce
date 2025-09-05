package org.example.ecommerce.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import org.example.ecommerce.entity.AddressEntity;
import org.example.ecommerce.entity.OrderEntity;
import org.example.ecommerce.entity.ProductEntity;
import org.example.ecommerce.entity.UserEntity;
import org.example.ecommerce.exception.ResourceNotFoundException;
import org.example.ecommerce.model.NewOrder;
import org.example.ecommerce.model.Order;
import org.example.ecommerce.repository.AddressRepository;
import org.example.ecommerce.repository.OrderRepository;
import org.example.ecommerce.repository.ProductRepository;
import org.example.ecommerce.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final AddressRepository addressRepository;
    private final ProductRepository productRepository;

    @Transactional
    public Optional<Order> addOrder(NewOrder newOrder) {
        // 1. Fetch parent entities
        UserEntity user = userRepository.findById(UUID.fromString(newOrder.getCustomerId()))
            .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + newOrder.getCustomerId()));
        AddressEntity address = addressRepository.findById(UUID.fromString(newOrder.getAddressId()))
            .orElseThrow(() -> new ResourceNotFoundException("Address not found with ID: " + newOrder.getAddressId()));

        // 2. Fetch products with lock for validation
        List<UUID> productIds = newOrder.getItems().stream()
            .map(item -> UUID.fromString(item.getId()))
            .collect(Collectors.toList());
        Map<String, ProductEntity> productMap = productRepository.findByIdInWithLock(productIds).stream()
            .collect(Collectors.toMap(p -> p.getId().toString(), Function.identity()));

        // 3. Create the Order aggregate
        OrderEntity orderEntity = OrderEntity.create(user, address, newOrder.getItems(), productMap);

        // 4. Deduct stock
        orderEntity.getOrderItems().forEach(orderItem -> {
            ProductEntity product = orderItem.getProductEntity();
            product.deductStock(orderItem.getQuantity());
        });

        // 5. Save order
        orderRepository.save(orderEntity);

        return Optional.of(toModel(orderEntity));
    }

    public Iterable<Order> getOrdersByCustomerId(String customerId) {
        return orderRepository.findByCustomerId(UUID.fromString(customerId)).stream().map(this::toModel).collect(Collectors.toList());
    }

    public Optional<Order> getByOrderId(String id) {
        return orderRepository.findById(UUID.fromString(id)).map(this::toModel);
    }

    private Order toModel(OrderEntity entity) {
        Order order = new Order();
        BeanUtils.copyProperties(entity, order);
        return order;
    }
}