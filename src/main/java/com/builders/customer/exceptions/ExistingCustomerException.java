package com.builders.customer.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class ExistingCustomerException extends RuntimeException {

  public ExistingCustomerException() {
    super("Cliente já existente em nossa base com estes dados");
  }

}
