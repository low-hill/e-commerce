package org.example.ecommerce.service;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import lombok.RequiredArgsConstructor;
import org.example.ecommerce.entity.ItemEntity;
import org.example.ecommerce.entity.ProductEntity;
import org.example.ecommerce.exception.ResourceNotFoundException;
import org.example.ecommerce.model.Item;
import org.example.ecommerce.repository.ProductRepository;
import org.springframework.stereotype.Service;

import static java.util.stream.Collectors.toList;

@RequiredArgsConstructor
@Service
public class ItemServiceImpl implements ItemService {
    private final ProductRepository productRepository;

    @Override
    public Item toModel(ItemEntity item) {
        Item m = new Item();
        m.id(item.getProduct().getId().toString()).unitPrice(item.getUnitPrice()).quantity(item.getQuantity());
        return m;
    }

    @Override
    public ItemEntity toEntity(Item item) {
        if (Objects.isNull(item)) {
            return null;
        }
        ProductEntity product = productRepository.findById(UUID.fromString(item.getId()))
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        return ItemEntity.builder()
                .product(product)
                .unitPrice(item.getUnitPrice())
                .quantity(item.getQuantity())
                .build();
    }

    @Override
    public List<Item> toModelList(List<ItemEntity> items) {
        if (Objects.isNull(items)) {
            return Collections.emptyList();
        }
        return items.stream()
                .map(e -> toModel(e)).collect(toList());
    }
}
