package org.example.ecommerce.entity;

import java.util.Collections;
import java.util.List;
import java.util.UUID;
import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import static jakarta.persistence.FetchType.LAZY;


@Entity
@Table(name = "user")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@ToString(exclude = {"addresses", "cards", "cart", "orders"})
public class UserEntity {
    @Id
    @GeneratedValue
    @Column(name = "ID", updatable = false, nullable = false)
    private UUID id;

    @NotNull(message = "User name is required.")
    @Basic(optional = false)
    @Column(name = "USERNAME")
    private String username;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "FIRST_NAME")
    private String firstName;

    @Column(name = "LAST_NAME")
    private String lastName;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "PHONE")
    private String phone;

    @Column(name = "USER_STATUS")
    private String userStatus;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinTable(
            name = "USER_ADDRESS",
            joinColumns = @jakarta.persistence.JoinColumn(name = "USER_ID"),
            inverseJoinColumns = @jakarta.persistence.JoinColumn(name = "ADDRESS_ID")
    )
    @Builder.Default
    private List<AddressEntity> addresses = Collections.emptyList();

    @OneToMany(mappedBy = "user", fetch = LAZY, orphanRemoval = true)
    @ToString.Exclude
    private List<CardEntity> cards;

    @OneToOne(mappedBy = "user", fetch = LAZY, orphanRemoval = true)
    @ToString.Exclude
    private CartEntity cart;

    @OneToMany(mappedBy = "userEntity", fetch = LAZY, orphanRemoval = true)
    @ToString.Exclude
    private List<OrderEntity> orders;
}