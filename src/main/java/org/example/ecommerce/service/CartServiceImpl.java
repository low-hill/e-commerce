package org.example.ecommerce.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import lombok.RequiredArgsConstructor;
import org.example.ecommerce.entity.CartEntity;
import org.example.ecommerce.entity.ItemEntity;
import org.example.ecommerce.exception.ItemAlreadyExistsException;
import org.example.ecommerce.exception.ItemNotFoundException;
import org.example.ecommerce.model.Item;
import org.example.ecommerce.repository.CartRepository;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
public class CartServiceImpl implements CartService {

  private final CartRepository cartRepository;
  private final ItemService itemService;

  @Override
  public List<Item> getCartItemsByCustomerId(String customerId) {
    CartEntity entity = getCartByCustomerId(customerId);
    return itemService.toModelList(entity.getItems());
  }

  @Override
  public Item getCartItemsByItemId(String customerId, String itemId) {
    CartEntity entity = getCartByCustomerId(customerId);

    ItemEntity itemEntity = entity.getItems().stream()
        .filter(i -> i.getProduct().getId().equals(UUID.fromString(itemId)))
        .findFirst()
        .orElseThrow(() -> new ItemNotFoundException(String.format(" - %s", itemId)));

    return itemService.toModel(itemEntity);
  }

  @Override
  public CartEntity getCartByCustomerId(String customerId) {
    return cartRepository.findByCustomerIdWithItems(UUID.fromString(customerId)).orElseGet(() -> CartEntity.builder().build());
  }

  @Override
  public List<Item> addCartItemsByCustomerId(String customerId, Item item) {
    CartEntity cart = getCartByCustomerId(customerId);

    boolean itemExists = cart.getItems().stream()
        .anyMatch(existingItem -> existingItem.getProduct().getId().toString().equals(item.getId()));

    if (itemExists) {
      throw new ItemAlreadyExistsException(
          String.format("Item with product ID (%s) already exists in the cart. You can update its quantity.", item.getId()));
    }

    ItemEntity newItemEntity = itemService.toEntity(item);
    cart.getItems().add(newItemEntity); // Add the new item entity to the cart's list
    return itemService.toModelList(cartRepository.save(cart).getItems());
  }

  @Override
  public List<Item> addOrReplaceItemsByCustomerId(String customerId, Item item) {
    CartEntity cart = getCartByCustomerId(customerId);

    // Find if the item already exists in the cart
    Optional<ItemEntity> existingItemOptional = cart.getItems().stream()
        .filter(i -> i.getProduct().getId().toString().equals(item.getId()))
        .findFirst();

    if (existingItemOptional.isPresent()) {
      // If item exists, update its quantity and price
      ItemEntity itemToUpdate = existingItemOptional.get();
      itemToUpdate.setQuantity(item.getQuantity());
      itemToUpdate.setUnitPrice(item.getUnitPrice());
    } else {
      // If item does not exist, create a new one and add it to the cart
      ItemEntity newItemEntity = itemService.toEntity(item);
      cart.getItems().add(newItemEntity);
    }

    // Save the cart and return the updated list of items
    return itemService.toModelList(cartRepository.save(cart).getItems());
  }

  @Override
  public void deleteItemFromCart(String customerId, String itemId) {

  }

  @Override
  public void deleteCart(String customerId) {

  }
}