package org.example.ecommerce.controller;

import java.util.UUID;

import java.math.BigDecimal;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.ecommerce.model.NewOrder;
import org.example.ecommerce.testdata.OrderTestData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
public class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private NewOrder newOrder;
    private final String testCustomerId = "a1b9b31d-e73c-4112-af7c-b68530f38222";
    private final String testAddressId = "a731fda1-aaad-42ea-bdbc-a27eeebe2cc0";
    private final String testProductId = "6d62d909-f957-430e-8689-b5129c0bb75e";
    private final String existingOrderId = "0a59ba9f-629e-4445-8129-b9bce1985d6a";

    private static final String URI = "/api/v1/orders";

    @BeforeEach
    public void setUp() {
        newOrder = OrderTestData.createNewOrderModel(UUID.fromString(testCustomerId), UUID.fromString(testAddressId), UUID.fromString(testProductId), new BigDecimal("17.15"));
    }

    @Test
    @DisplayName("POST /api/v1/orders - Add a new order")
    void addOrder() throws Exception {
        // when
        ResultActions result = mockMvc.perform(post(URI)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newOrder)))
                .andDo(print());
        // then
        result.andExpect(status().isOk());
    }

    @Test
    @DisplayName("GET /api/v1/orders - Get orders by customer ID")
    void getOrdersByCustomerId() throws Exception {
        mockMvc.perform(get(URI)
                        .accept(MediaType.APPLICATION_JSON)
                        .param("customerId", testCustomerId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(existingOrderId));
    }

    @Test
    @DisplayName("GET /api/v1/orders/{id} - Get order by ID")
    void getByOrderId_Success() throws Exception {
        mockMvc.perform(get(URI + "/{id}", existingOrderId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(existingOrderId));
    }

    @Test
    @DisplayName("GET /api/v1/orders/{id} - Order not found")
    void getByOrderId_NotFound() throws Exception {
        mockMvc.perform(get(URI + "/{id}", UUID.randomUUID().toString()))
                .andExpect(status().isNotFound());
    }
}