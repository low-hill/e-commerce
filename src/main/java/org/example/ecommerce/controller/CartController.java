package org.example.ecommerce.controller;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.example.ecommerce.api.CartApi;
import org.example.ecommerce.model.Item;
import org.example.ecommerce.service.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequiredArgsConstructor
public class CartController implements CartApi {

    private final CartService cartService;

    @Override
    public ResponseEntity<List<Item>> addCartItemsByCustomerId(String customerId, Item item) {
        return ok(cartService.addCartItemsByCustomerId(customerId, item));
    }

    @Override
    public ResponseEntity<List<Item>> addOrReplaceItemsByCustomerId(String customerId, Item item) {
        return ok(cartService.addOrReplaceItemsByCustomerId(customerId, item));
    }

    @Override
    public ResponseEntity<List<Item>> getCartItemsByCustomerId(String customerId){
        return ok(cartService.getCartItemsByCustomerId(customerId));
    }
}
