package org.example.ecommerce.entity;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.ecommerce.config.generator.GeneratedUuidV7;


@Entity
@Table(name = "product")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
public class ProductEntity {

    @Id
    @GeneratedUuidV7
    @Column(name = "ID", updatable = false, nullable = false)
    private UUID id;

    @NotNull(message = "Product name is required.")
    @Basic(optional = false)
    @Column(name = "NAME")
    private String name;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "PRICE")
    private BigDecimal price;

    @Column(name = "COUNT")
    private int count;

    @Column(name = "IMAGE_URL")
    private String imageUrl;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "PRODUCT_TAG",
            joinColumns = @JoinColumn(name = "PRODUCT_ID"),
            inverseJoinColumns = @JoinColumn(name = "TAG_ID")
    )
    @Builder.Default
    private List<TagEntity> tags = Collections.emptyList();

    @OneToMany(mappedBy = "product")
    private List<ItemEntity> items;
}