package org.example.ecommerce.entity;

import java.util.Collections;
import java.util.List;
import java.util.UUID;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.example.ecommerce.config.generator.GeneratedUuidV7;


@Entity
@Table(name = "cart")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@ToString(exclude = {"user", "items"})
@EqualsAndHashCode(exclude = {"user", "items"})
public class CartEntity {

    @Id
    @GeneratedUuidV7
    @Column(name = "ID", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "USER_ID", insertable = false, updatable = false)
    private UUID userId;

//    @OneToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "USER_ID", referencedColumnName = "ID")
//    private UserEntity user;

    @ManyToMany(
            cascade = CascadeType.ALL
    )
    @JoinTable(
            name = "CART_ITEM",
            joinColumns = @JoinColumn(name = "CART_ID"),
            inverseJoinColumns = @JoinColumn(name = "ITEM_ID")
    )
    @Builder.Default
    private List<ItemEntity> items = Collections.emptyList();
}
