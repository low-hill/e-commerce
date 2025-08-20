package org.example.ecommerce.service;

import java.util.List;

import org.example.ecommerce.entity.ItemEntity;
import org.example.ecommerce.model.Item;

public interface ItemService {
    Item toModel(ItemEntity item);
    List<Item> toModelList(List<ItemEntity> items);

    ItemEntity toEntity(Item item);
}
