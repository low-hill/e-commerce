package org.example.ecommerce.hateoas;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import org.example.ecommerce.controller.OrderController;
import org.example.ecommerce.entity.OrderEntity;
import org.example.ecommerce.model.Order;
import org.springframework.beans.BeanUtils;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class OrderRepresentationModelAssembler extends
        RepresentationModelAssemblerSupport<OrderEntity, Order> {

    public OrderRepresentationModelAssembler() {
        super(OrderController.class, Order.class);
    }

    @Override
    public Order toModel(OrderEntity entity) {
        Order resource = createModelWithId(entity.getId(), entity);
        BeanUtils.copyProperties(entity, resource);
        resource.setId(entity.getId().toString());
        // Add links specific to Order if needed
        resource.add(linkTo(methodOn(OrderController.class).getByOrderId(entity.getId().toString()))
                .withSelfRel());
        resource.add(linkTo(methodOn(OrderController.class).getOrdersByCustomerId(entity.getUserEntity().getId().toString()))
                .withRel("customer-orders"));
        return resource;
    }

    public List<Order> toListModel(List<OrderEntity> orderEntities) {
        if(Objects.isNull(orderEntities)){
            return Collections.emptyList();
        }

        return orderEntities.stream().map(this::toModel).toList();
    }
}
