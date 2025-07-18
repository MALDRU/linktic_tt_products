package com.linktic.products.core.products.services;

import com.linktic.products.controllerAdvice.exceptions.ResourceNotFoundException;
import com.linktic.products.core.products.dto.CreateProductRequest;
import com.linktic.products.core.products.dto.CreateProductResponse;
import com.linktic.products.core.products.dto.ProductByIdResponse;
import com.linktic.products.core.products.dto.ProductPageResponse;
import com.linktic.products.core.products.repositories.ProductRepository;
import com.linktic.products.pagination.PaginatedResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {

  private final ProductRepository productRepository;

  public CreateProductResponse createProduct(final CreateProductRequest createProductRequest) {
    return new CreateProductResponse(productRepository.save(createProductRequest.toEntity()));
  }

  public ProductByIdResponse searchProductById(final Long id) {
    return new ProductByIdResponse(
        productRepository
            .findById(id)
            .orElseThrow(
                () ->
                    new ResourceNotFoundException(
                        String.format("Product with id %s not found", id))));
  }

  public PaginatedResponse<ProductPageResponse> getAllProducts(final Pageable pageable) {
    var content = productRepository.findAll(pageable).map(ProductPageResponse::new);
    return new PaginatedResponse<>(content);
  }
}
