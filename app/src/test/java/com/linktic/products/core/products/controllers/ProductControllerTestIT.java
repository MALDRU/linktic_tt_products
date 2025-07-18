package com.linktic.products.core.products.controllers;

import com.linktic.products.core.products.dto.CreateProductRequest;
import com.linktic.products.core.products.dto.CreateProductResponse;
import com.linktic.products.core.products.dto.ProductByIdResponse;
import com.linktic.products.core.products.dto.ProductPageResponse;
import com.linktic.products.core.products.entities.Product;
import com.linktic.products.core.products.repositories.ProductRepository;
import com.linktic.products.pagination.PaginatedResponse;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
public class ProductControllerTestIT {

  @Container
  static PostgreSQLContainer<?> postgres =
      new PostgreSQLContainer<>("postgres:15")
          .withDatabaseName("test-db")
          .withUsername("test")
          .withPassword("test");

  @DynamicPropertySource
  static void properties(DynamicPropertyRegistry registry) {
    registry.add("spring.datasource.url", postgres::getJdbcUrl);
    registry.add("spring.datasource.username", postgres::getUsername);
    registry.add("spring.datasource.password", postgres::getPassword);
  }

  @Autowired private TestRestTemplate restTemplate;

  @Autowired private ProductRepository productRepository;

  @Test
  void getAllProducts() {
    productRepository.deleteAll();
    productRepository.saveAll(
        List.of(
            Product.builder().name("product 1").price(20.0).build(),
            Product.builder().name("product 2").price(50.0).build(),
            Product.builder().name("product 3").price(50.0).build(),
            Product.builder().name("product 4").price(80.0).build()));

    var url = "/v1/products?page=0&size=2";

    ResponseEntity<PaginatedResponse<ProductPageResponse>> response =
        restTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<>() {});

    Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());

    var contentResponse = response.getBody();
    Assertions.assertNotNull(contentResponse);
    Assertions.assertEquals(0, contentResponse.getPage());
    Assertions.assertEquals(2, contentResponse.getSize());
    Assertions.assertEquals(2, contentResponse.getContent().size());
    Assertions.assertEquals(4, contentResponse.getTotalElements());
  }

  @Test
  void searchProductById() {
    var productFound =
        productRepository.save(Product.builder().name("product found").price(80.0).build());

    var url = "/v1/products/" + productFound.getId();

    ResponseEntity<ProductByIdResponse> response =
        restTemplate.exchange(url, HttpMethod.GET, null, ProductByIdResponse.class);

    Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());

    var contentResponse = response.getBody();
    Assertions.assertNotNull(contentResponse);
    Assertions.assertEquals(productFound.getId(), contentResponse.getId());
    Assertions.assertEquals(productFound.getName(), contentResponse.getName());
    Assertions.assertEquals(productFound.getPrice(), contentResponse.getPrice());
  }

  @Test
  void searchProductByIdWhenNotFound() {
    var url = "/v1/products/0";

    ResponseEntity<ProductByIdResponse> response =
        restTemplate.exchange(url, HttpMethod.GET, null, ProductByIdResponse.class);

    Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
  }

  @Test
  void createProduct() {
    var url = "/v1/products";

    ResponseEntity<CreateProductResponse> response =
        restTemplate.postForEntity(
            url,
            CreateProductRequest.builder().name("product 5").price(100.0).build(),
            CreateProductResponse.class);

    Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());

    var body = response.getBody();
    Assertions.assertNotNull(body);
    Assertions.assertNotNull(body.getId());
  }
}
