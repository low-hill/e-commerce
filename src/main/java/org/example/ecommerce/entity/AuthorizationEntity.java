package org.example.ecommerce.entity;

import java.sql.Timestamp;
import java.util.UUID;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import org.hibernate.annotations.GenericGenerator;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "authorization")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class AuthorizationEntity {

    @Id
    @GeneratedValue(generator = "uuid-v7")
    @GenericGenerator(name = "uuid-v7", type = org.example.ecommerce.config.UuidV7Generator.class)
    @Column(name = "ID", updatable = false, nullable = false)
    private UUID id;

    @Column(name="AUTHORIZED")
    private boolean authorized;

    @Column(name="TIME")
    private Timestamp time;

    @Column(name = "MESSAGE")
    private String message;

    @Column(name = "ERROR")
    private String error;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ORDER_ID", referencedColumnName = "id")
    private OrderEntity orderEntity;
}