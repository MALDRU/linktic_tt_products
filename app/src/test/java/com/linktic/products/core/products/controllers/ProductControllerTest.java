package com.linktic.products.core.products.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.linktic.products.controllerAdvice.exceptions.ResourceNotFoundException;
import com.linktic.products.core.products.dto.CreateProductRequest;
import com.linktic.products.core.products.dto.CreateProductResponse;
import com.linktic.products.core.products.dto.ProductByIdResponse;
import com.linktic.products.core.products.dto.ProductPageResponse;
import com.linktic.products.core.products.services.ProductService;
import com.linktic.products.pagination.PaginatedResponse;
import java.util.List;
import java.util.function.Function;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(ProductController.class)
class ProductControllerTest {

  @MockitoBean private ProductService productService;

  @Autowired private MockMvc mockMvc;

  @Autowired private ObjectMapper objectMapper;

  private final Function<String, String> getPathBase = path -> "/v1/products" + path;

  @Test
  void getAllProducts() throws Exception {
    Mockito.when(productService.getAllProducts(ArgumentMatchers.any()))
        .thenReturn(new PaginatedResponse<>(new PageImpl<>(List.of(new ProductPageResponse()))));
    this.mockMvc
        .perform(MockMvcRequestBuilders.get(getPathBase.apply("")))
        .andDo(MockMvcResultHandlers.print())
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE));
  }

  @Test
  void searchProductById() throws Exception {
    Mockito.when(productService.searchProductById(ArgumentMatchers.anyLong()))
        .thenReturn(new ProductByIdResponse());
    this.mockMvc
        .perform(MockMvcRequestBuilders.get(getPathBase.apply("/1")))
        .andDo(MockMvcResultHandlers.print())
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE));
  }

  @Test
  void searchProductByIdWhenNotFound() throws Exception {
    Mockito.when(productService.searchProductById(ArgumentMatchers.anyLong()))
        .thenThrow(new ResourceNotFoundException(""));
    this.mockMvc
        .perform(MockMvcRequestBuilders.get(getPathBase.apply("/1")))
        .andDo(MockMvcResultHandlers.print())
        .andExpect(MockMvcResultMatchers.status().isNotFound())
        .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE));
  }

  @Test
  void createProduct() throws Exception {
    Mockito.when(productService.createProduct(ArgumentMatchers.any()))
        .thenReturn(new CreateProductResponse());
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.post(getPathBase.apply(""))
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    objectMapper.writeValueAsString(
                        CreateProductRequest.builder()
                            .name("name")
                            .price(1.0)
                            .description("description")
                            .build())))
        .andDo(MockMvcResultHandlers.print())
        .andExpect(MockMvcResultMatchers.status().isCreated())
        .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE));
  }

  @Test
  void createProductWhenNoSendName() throws Exception {
    Mockito.when(productService.createProduct(ArgumentMatchers.any()))
        .thenReturn(new CreateProductResponse());
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.post(getPathBase.apply(""))
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    objectMapper.writeValueAsString(
                        CreateProductRequest.builder()
                            .price(1.0)
                            .description("description")
                            .build())))
        .andDo(MockMvcResultHandlers.print())
        .andExpect(MockMvcResultMatchers.status().isBadRequest());
  }

  @Test
  void createProductWhenNameMaxLengthRestriction() throws Exception {
    Mockito.when(productService.createProduct(ArgumentMatchers.any()))
        .thenReturn(new CreateProductResponse());
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.post(getPathBase.apply(""))
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    objectMapper.writeValueAsString(
                        CreateProductRequest.builder()
                            .name("a".repeat(61))
                            .description("description")
                            .price(1.0)
                            .build())))
        .andDo(MockMvcResultHandlers.print())
        .andExpect(MockMvcResultMatchers.status().isBadRequest());
  }

  @Test
  void createProductWhenNoSendPrice() throws Exception {
    Mockito.when(productService.createProduct(ArgumentMatchers.any()))
        .thenReturn(new CreateProductResponse());
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.post(getPathBase.apply(""))
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    objectMapper.writeValueAsString(
                        CreateProductRequest.builder()
                            .name("name")
                            .description("description")
                            .build())))
        .andDo(MockMvcResultHandlers.print())
        .andExpect(MockMvcResultMatchers.status().isBadRequest());
  }

  @Test
  void createProductWhenDescriptionMaxLengthRestriction() throws Exception {
    Mockito.when(productService.createProduct(ArgumentMatchers.any()))
        .thenReturn(new CreateProductResponse());
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.post(getPathBase.apply(""))
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    objectMapper.writeValueAsString(
                        CreateProductRequest.builder()
                            .name("abc")
                            .description("d".repeat(257))
                            .price(1.0)
                            .build())))
        .andDo(MockMvcResultHandlers.print())
        .andExpect(MockMvcResultMatchers.status().isBadRequest());
  }
}
