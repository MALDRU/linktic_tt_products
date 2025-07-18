package com.linktic.products.controllerAdvice;

import com.linktic.products.controllerAdvice.exceptions.ResourceNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

  @ExceptionHandler(ResourceNotFoundException.class)
  public ResponseEntity<ErrorResponse> handleResourceNotFound(final ResourceNotFoundException ex) {
    return new ResponseEntity<>(
        ErrorResponse.builder().code(HttpStatus.NOT_FOUND.name()).message(ex.getMessage()).build(),
        HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(DataAccessException.class)
  public ResponseEntity<ErrorResponse> handleDataAccess(final DataAccessException ex) {
    log.error("Error making database request {}", ex.getMessage());
    return new ResponseEntity<>(
        ErrorResponse.builder()
            .code(HttpStatus.NOT_FOUND.name())
            .message("Error making database request")
            .build(),
        HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
