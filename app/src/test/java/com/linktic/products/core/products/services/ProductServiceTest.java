package com.linktic.products.core.products.services;

import com.linktic.products.controllerAdvice.exceptions.ResourceNotFoundException;
import com.linktic.products.core.products.dto.CreateProductRequest;
import com.linktic.products.core.products.entities.Product;
import com.linktic.products.core.products.repositories.ProductRepository;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

@SpringBootTest(classes = ProductService.class)
class ProductServiceTest {

  @MockitoBean private ProductRepository productRepository;

  @Autowired private ProductService productService;

  @Test
  void createProduct() {
    Mockito.when(productRepository.save(ArgumentMatchers.any()))
        .thenReturn(Product.builder().id(1L).build());
    var response =
        productService.createProduct(
            CreateProductRequest.builder()
                .name("name")
                .price(1.0)
                .description("description")
                .build());
    Assertions.assertNotNull(response);
    Assertions.assertEquals(1L, response.getId());
  }

  @Test
  void searchProductById() {
    Mockito.when(productRepository.findById(ArgumentMatchers.anyLong()))
        .thenReturn(Optional.of(Product.builder().id(1L).build()));
    var response = productService.searchProductById(1L);
    Assertions.assertNotNull(response);
    Assertions.assertEquals(1L, response.getId());
  }

  @Test
  void searchProductByIdWhenNotFound() {
    Mockito.when(productRepository.findById(ArgumentMatchers.anyLong()))
        .thenReturn(Optional.empty());
    var response =
        Assertions.assertThrows(
            ResourceNotFoundException.class, () -> productService.searchProductById(1L));
    Assertions.assertNotNull(response);
    Assertions.assertEquals("Product with id 1 not found", response.getMessage());
  }

  @Test
  void getAllProducts() {
    Mockito.when(productRepository.findAll(ArgumentMatchers.any(Pageable.class)))
        .thenReturn(new PageImpl<>(List.of(new Product())));
    var response = productService.getAllProducts(Pageable.unpaged());
    Assertions.assertNotNull(response);
    Assertions.assertEquals(1L, response.getTotalElements());
  }
}
