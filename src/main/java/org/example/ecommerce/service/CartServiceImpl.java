package org.example.ecommerce.service;

import java.util.List;
import java.util.UUID;

import lombok.RequiredArgsConstructor;
import org.example.ecommerce.entity.CartEntity;
import org.example.ecommerce.entity.ItemEntity;
import org.example.ecommerce.exception.ItemAlreadyExistsException;
import org.example.ecommerce.exception.ItemNotFoundException;
import org.example.ecommerce.model.Item;
import org.example.ecommerce.repository.CartRepository;
import org.example.ecommerce.repository.UserRepository;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
public class CartServiceImpl implements CartService {

  private final CartRepository cartRepository;
  private final UserRepository userRepo;
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
//    return cartRepository.findByCustomerIdWithItems(UUID.fromString(customerId)
//        .orElseGet(() -> {
//          UserEntity user = userRepo.findById(UUID.fromString(customerId))
//              .orElseThrow(() -> new CustomerNotFoundException(
//                  String.format(" - %s", customerId)));
//          return CartEntity.builder().user(user).build();
//        });
  }

  @Override
  public List<Item> addCartItemsByCustomerId(String customerId, Item item) {
    CartEntity cart = getCartByCustomerId(customerId);

    boolean itemExists = cart.getItems().stream()
        .anyMatch(existingItem -> existingItem.getProduct().getId().equals(item.getId()));

    if (itemExists) {
      throw new ItemAlreadyExistsException(
          String.format("Item with product ID (%s) already exists in the cart. You can update its quantity.", item.getId()));
    }

    ItemEntity newItemEntity = itemService.toEntity(item);
    cart.getItems().add(newItemEntity); // Add the new item entity to the cart's list
    return itemService.toModelList(cartRepository.save(cart).getItems());
  }

  @Override
  public List<Item> addOrReplaceItemsByCustomerId(String customerId, Item itemId) {
    return null;
  }

  @Override
  public void deleteItemFromCart(String customerId, String itemId) {

  }

  @Override
  public void deleteCart(String customerId) {

  }
}