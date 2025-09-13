package org.example.ecommerce.entity;


import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.example.ecommerce.config.generator.GeneratedUuidV7;
import org.example.ecommerce.exception.ResourceNotFoundException;
import org.example.ecommerce.model.Item;
import org.example.ecommerce.model.Order.StatusEnum;



@Entity
@Table(name = "orders")
@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@ToString(exclude = {"userEntity", "addressEntity", "paymentEntity", "shipment", "cardEntity", "items", "authorizationEntity"})
@EqualsAndHashCode(exclude = {"userEntity", "addressEntity", "paymentEntity", "shipment", "cardEntity", "items", "authorizationEntity"})
public class OrderEntity {
    @Id
    @GeneratedUuidV7
    @Column(name = "ID", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "TOTAL")
    private BigDecimal totalAmount;

    @Column(name = "STATUS")
    @Enumerated(EnumType.STRING)
    private StatusEnum status;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name="CUSTOMER_ID", nullable=false)
    private UserEntity userEntity;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "ADDRESS_ID", referencedColumnName = "ID")
    private AddressEntity addressEntity;

    @OneToOne(cascade = CascadeType.ALL )
    @JoinColumn(name = "PAYMENT_ID", referencedColumnName = "ID")
    private PaymentEntity paymentEntity;

    @JoinColumn(name = "SHIPMENT_ID", referencedColumnName = "ID")
    @OneToOne
    private ShipmentEntity shipment;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "CARD_ID", referencedColumnName = "ID")
    private CardEntity cardEntity;

    @Column(name = "ORDER_DATE")
    private OffsetDateTime orderDate;

    @OneToMany(mappedBy = "orderEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<OrderItemEntity> items = new ArrayList<>();

    @OneToOne(mappedBy = "orderEntity")
    private AuthorizationEntity authorizationEntity;

    public static OrderEntity create(UserEntity user, AddressEntity address, List<Item> items, Map<String, ProductEntity> productMap) {
        OrderEntity orderEntity = OrderEntity.builder()
            .userEntity(user)
            .addressEntity(address)
            .orderDate(OffsetDateTime.now())
            .status(StatusEnum.CREATED)
            .build();

        List<OrderItemEntity> orderItems = items.stream().map(item -> {
            ProductEntity product = productMap.get(item.getId());
            if (product == null) {
                throw new ResourceNotFoundException("Product with ID " + item.getId() + " not found.");
            }
            if (product.getPrice().compareTo(item.getUnitPrice()) != 0) {
                throw new IllegalStateException("Price for item " + item.getId() + " does not match the price in the database.");
            }
            
            return OrderItemEntity.builder()
                .orderEntity(orderEntity)
                .productEntity(product)
                .quantity(item.getQuantity())
                .unitPrice(item.getUnitPrice())
                .build();
        }).collect(Collectors.toList());

        BigDecimal total = orderItems.stream()
            .map(item -> item.getUnitPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
            .reduce(BigDecimal.ZERO, BigDecimal::add);

        orderEntity.setItems(orderItems);
        orderEntity.setTotalAmount(total);
        return orderEntity;
    }
}