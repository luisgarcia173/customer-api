package com.builders.customer.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class ExistingCustomerException extends RuntimeException {
  public ExistingCustomerException() {
    super("Cliente já existente em nossa base com estes dados");
  }

  public ExistingCustomerException(String message) {
    super(message);
  }

  public ExistingCustomerException(String message, Throwable cause) {
    super(message, cause);
  }

  public ExistingCustomerException(Throwable cause) {
    super(cause);
  }

  public ExistingCustomerException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
