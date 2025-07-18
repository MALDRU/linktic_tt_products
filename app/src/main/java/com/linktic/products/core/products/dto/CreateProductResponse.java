package com.linktic.products.core.products.dto;

import com.linktic.products.core.products.entities.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CreateProductResponse {
  private Long id;

  public CreateProductResponse(final Product product) {
    this.id = product.getId();
  }
}
