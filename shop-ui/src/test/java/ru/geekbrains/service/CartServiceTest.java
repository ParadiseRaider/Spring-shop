package ru.geekbrains.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.geekbrains.controller.repr.ProductRepr;
import ru.geekbrains.service.model.LineItem;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CartServiceTest {
    private CartService cartService;

    @BeforeEach
    public void init() {
        cartService = new CartServiceImpl();
    }

    @Test
    public void updateCartTest() {
        ProductRepr expectedProduct = new ProductRepr();
        expectedProduct.setId(1L);
        expectedProduct.setName("Product name");
        expectedProduct.setBrandName("Brand name");
        expectedProduct.setPrice(new BigDecimal(1300));

        cartService.updateCart(new LineItem(expectedProduct));
        List<LineItem> lineItems = cartService.getLineItems();
        assertNotNull(lineItems);
        assertEquals(1, lineItems.size());

        LineItem lineItem = lineItems.get(0);
        assertEquals("Product name", expectedProduct.getName());
        assertEquals(lineItem.getProductRepr().getName(), expectedProduct.getName());
        assertEquals(lineItem.getProductRepr().getBrandName(), expectedProduct.getBrandName());
        assertEquals(lineItem.getProductRepr().getPrice(), expectedProduct.getPrice());
        assertEquals(1L, lineItem.getProductId());
    }
}
