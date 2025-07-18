package com.linktic.products.pagination;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Page;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaginatedResponse<T> {
  private int page;
  private int size;
  private long totalElements;
  private int totalPages;
  private List<T> content;

  public PaginatedResponse(final Page<T> page) {
    this.page = page.getNumber();
    this.size = page.getSize();
    this.totalElements = page.getTotalElements();
    this.totalPages = page.getTotalPages();
    this.content = page.getContent();
  }
}
