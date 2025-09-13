package org.example.ecommerce.service;

import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

import org.example.ecommerce.entity.AddressEntity;
import org.example.ecommerce.entity.OrderEntity;
import org.example.ecommerce.entity.ProductEntity;
import org.example.ecommerce.entity.UserEntity;
import org.example.ecommerce.exception.ResourceNotFoundException;
import org.example.ecommerce.model.NewOrder;
import org.example.ecommerce.repository.AddressRepository;
import org.example.ecommerce.repository.OrderRepository;
import org.example.ecommerce.repository.ProductRepository;
import org.example.ecommerce.repository.UserRepository;
import org.example.ecommerce.testdata.AddressTestData;
import org.example.ecommerce.testdata.OrderTestData;
import org.example.ecommerce.testdata.ProductTestData;
import org.example.ecommerce.testdata.UserTestData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.anyList;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private AddressRepository addressRepository;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private OrderService orderService;

    private UserEntity testUser;
    private AddressEntity testAddress;
    private ProductEntity testProduct;
    private NewOrder newOrder;

    @BeforeEach
    void setUp() {
        testUser = UserTestData.createUserEntity();
        testAddress = AddressTestData.createAddressEntity();
        testProduct = ProductTestData.createProductEntity();
        newOrder = OrderTestData.createNewOrderModel(testUser.getId(), testAddress.getId(), testProduct.getId(), testProduct.getPrice());
    }

    @Test
    void addOrder_Success() {
        // given
        when(userRepository.findById(any(UUID.class))).thenReturn(Optional.of(testUser));
        when(addressRepository.findById(any(UUID.class))).thenReturn(Optional.of(testAddress));
        when(productRepository.findByIdInWithLock(anyList())).thenReturn(Collections.singletonList(testProduct));
        when(orderRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        // when
        Optional<OrderEntity> result = orderService.addOrder(newOrder);
        assertTrue(result.isPresent());
        assertEquals(1, result.get().getItems().size());

        // then
        assertTrue(result.isPresent());
        assertEquals(1, result.get().getItems().size());
        assertEquals(9, testProduct.getCount()); // 재고 감소 확인
        verify(orderRepository).save(any(OrderEntity.class));
    }

    @Test
    @DisplayName("주문 생성 - 상품 재고 부족")
    void addOrder_ProductOutOfStockException() {
        // given
        testProduct = ProductTestData.createProductEntity(10);

        // when & then
        IllegalStateException exception = assertThrows(
                IllegalStateException.class,
                () -> testProduct.deductStock(11)
        );
        assertEquals("Insufficient stock for product " + testProduct.getName(),
                exception.getMessage());

    }

    @Test
    void addOrder_UserNotFound() {
        // given
        when(userRepository.findById(any(UUID.class))).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> orderService.addOrder(newOrder));
    }

    @Test
    void addOrder_AddressNotFound() {
        when(userRepository.findById(any(UUID.class))).thenReturn(Optional.of(testUser));
        when(addressRepository.findById(any(UUID.class))).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> orderService.addOrder(newOrder));
    }
}