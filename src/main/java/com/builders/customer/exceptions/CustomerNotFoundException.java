package com.builders.customer.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CustomerNotFoundException extends RuntimeException {

  public CustomerNotFoundException() {
    super("Cliente não encontrado");
  }

  public CustomerNotFoundException(String message) {
    super(message);
  }

  public CustomerNotFoundException(String message, Throwable cause) {
    super(message, cause);
  }

  public CustomerNotFoundException(Throwable cause) {
    super(cause);
  }

  public CustomerNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
