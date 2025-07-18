package com.linktic.products.core.products.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "products")
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Product {
  @Id
  @GeneratedValue(generator = "products_sequence", strategy = GenerationType.SEQUENCE)
  @SequenceGenerator(
      name = "products_sequence",
      sequenceName = "products_sequence",
      allocationSize = 1)
  private Long id;

  private String name;
  private Double price;
  private String description;
}
