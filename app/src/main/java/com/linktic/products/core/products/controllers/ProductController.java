package com.linktic.products.core.products.controllers;

import com.linktic.products.core.products.dto.CreateProductRequest;
import com.linktic.products.core.products.dto.CreateProductResponse;
import com.linktic.products.core.products.dto.ProductByIdResponse;
import com.linktic.products.core.products.dto.ProductPageResponse;
import com.linktic.products.core.products.services.ProductService;
import com.linktic.products.pagination.PaginatedResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/products")
@RequiredArgsConstructor
public class ProductController {

  private final ProductService productService;

  @GetMapping
  public ResponseEntity<PaginatedResponse<ProductPageResponse>> getAllProducts(
      final Pageable pageable) {
    return ResponseEntity.ok(productService.getAllProducts(pageable));
  }

  @GetMapping("/{id}")
  public ResponseEntity<ProductByIdResponse> searchProductById(@PathVariable("id") final Long id) {
    return ResponseEntity.ok(productService.searchProductById(id));
  }

  @PostMapping
  public ResponseEntity<CreateProductResponse> createProduct(
      @Valid @RequestBody final CreateProductRequest createProductRequest) {
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(productService.createProduct(createProductRequest));
  }
}
