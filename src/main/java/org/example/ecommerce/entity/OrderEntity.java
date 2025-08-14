package org.example.ecommerce.entity;


import org.example.ecommerce.model.Order.StatusEnum;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import org.hibernate.annotations.GenericGenerator;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;



@Entity
@Table(name = "orders")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@ToString(exclude = {"userEntity", "addressEntity", "paymentEntity", "shipment", "cardEntity", "items", "authorizationEntity"})
@EqualsAndHashCode(exclude = {"userEntity", "addressEntity", "paymentEntity", "shipment", "cardEntity", "items", "authorizationEntity"})
public class OrderEntity {
    @Id
    @GeneratedValue(generator = "uuid-v7")
    @GenericGenerator(name = "uuid-v7", type = org.example.ecommerce.config.UuidV7Generator.class)
    @Column(name = "ID", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "TOTAL")
    private BigDecimal total;

    @Column(name = "STATUS")
    @Enumerated(EnumType.STRING)
    private StatusEnum status;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name="CUSTOMER_ID", nullable=false)
    private UserEntity userEntity;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "ADDRESS_ID", referencedColumnName = "ID", insertable=false, updatable=false)
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
    private Timestamp orderDate;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(
            name = "ORDER_ITEM",
            joinColumns = @JoinColumn(name = "ORDER_ID"),
            inverseJoinColumns = @JoinColumn(name = "ITEM_ID")
    )
    @Builder.Default
    private List<ItemEntity> items = Collections.emptyList();

    @OneToOne(mappedBy = "orderEntity")
    private AuthorizationEntity authorizationEntity;
}