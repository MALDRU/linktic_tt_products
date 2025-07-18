package com.linktic.products.core.products.dto;

import com.linktic.products.core.products.entities.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductByIdResponse {
  private Long id;
  private String name;
  private String description;
  private Double price;

  public ProductByIdResponse(final Product product) {
    this.id = product.getId();
    this.name = product.getName();
    this.description = product.getDescription();
    this.price = product.getPrice();
  }
}
