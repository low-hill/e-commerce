package org.example.ecommerce.controller;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.example.ecommerce.api.ProductApi;
import org.example.ecommerce.hateoas.ProductRepresentationModelAssembler;
import org.example.ecommerce.model.Product;
import org.example.ecommerce.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequiredArgsConstructor
public class ProductController implements ProductApi {

    private final ProductService productService;
    private final ProductRepresentationModelAssembler assembler;

    @Override
    public ResponseEntity<Product> getProduct(String id) {
        return productService.getProduct(id)
                .map(assembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<List<Product>> queryProducts(String tag, String name, Integer page, Integer size) {
        return ok(assembler.toListModel(productService.getAllProducts()));
    }
}