package com.farmapp.model;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

public class ProductTest {

    @Test
    public void testProductConstructor() {
        Product product = new Product(1L, "Apple", "Fresh Red Apples", "Fruit", BigDecimal.valueOf(100), "kg", BigDecimal.valueOf(3.5), "image_url");

        assertEquals("Apple", product.getName());
        assertEquals("Fresh Red Apples", product.getDescription());
        assertEquals("Fruit", product.getCategory());
        assertEquals(BigDecimal.valueOf(100), product.getQuantity());
    }
}
