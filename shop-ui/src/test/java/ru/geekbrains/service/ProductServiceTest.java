package ru.geekbrains.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.geekbrains.controller.repr.ProductRepr;
import ru.geekbrains.model.Brand;
import ru.geekbrains.model.Category;
import ru.geekbrains.model.Product;
import ru.geekbrains.repo.ProductRepository;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ProductServiceTest {
    private ProductRepository productRepository;
    private ProductService productService;

    @BeforeEach
    public void init() {
        productRepository = mock(ProductRepository.class);
        productService = new ProductServiceImpl();
        ((ProductServiceImpl)productService).setProductRepository(productRepository);
    }

    @Test
    public void findByIdText() {
        Category expectedCategory = new Category();
        expectedCategory.setId(1L);
        expectedCategory.setName("Category name");

        Brand expectedBrand = new Brand();
        expectedBrand.setId(1L);
        expectedBrand.setName("Brand name");

        Product expectedProduct = new Product();
        expectedProduct.setId(1L);
        expectedProduct.setName("Product name");
        expectedProduct.setCategory(expectedCategory);
        expectedProduct.setBrand(expectedBrand);
        expectedProduct.setPictures(new ArrayList<>());
        expectedProduct.setPrice(new BigDecimal(1234));

        when(productRepository.findById(eq(1L)))
                .thenReturn(Optional.of(expectedProduct));

        Optional<ProductRepr> opt = productService.findById(1L);
        assertTrue(opt.isPresent());
        assertEquals(expectedProduct.getId(), opt.get().getId());
        assertEquals(expectedProduct.getName(), opt.get().getName());
        assertEquals(expectedCategory.getName(),opt.get().getCategoryName());
        assertEquals(expectedBrand.getName(),opt.get().getBrandName());
    }
}
