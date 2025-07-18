package com.linktic.products.controllerAdvice.exceptions;

public class ResourceNotFoundException extends RuntimeException {
  public ResourceNotFoundException(final String message) {
    super(message);
  }
}
