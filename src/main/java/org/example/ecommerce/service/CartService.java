package org.example.ecommerce.service;

import java.util.List;

import org.example.ecommerce.entity.CartEntity;
import org.example.ecommerce.model.Item;

public interface CartService {
    List<Item> getCartItemsByCustomerId(String customerId);
    Item getCartItemsByItemId(String customerId, String itemId);
    CartEntity getCartByCustomerId(String customerId);
    List<Item> addCartItemsByCustomerId(String customerId, Item item);

    List<Item> addOrReplaceItemsByCustomerId(String customerId, Item itemId);

    void deleteItemFromCart(String customerId, String itemId);

    void deleteCart(String customerId);
}
