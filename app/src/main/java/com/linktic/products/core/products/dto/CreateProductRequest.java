package com.linktic.products.core.products.dto;

import com.linktic.products.core.products.entities.Product;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CreateProductRequest {
  @NotEmpty @Size(max = 60) private String name;

  @Size(max = 255) private String description;

  @NotNull private Double price;

  public Product toEntity() {
    return Product.builder().name(name).price(price).description(description).build();
  }
}
