package com.builders.customer.resources.handler;

import com.builders.customer.exceptions.CustomerNotFoundException;
import com.builders.customer.exceptions.ExistingCustomerException;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalDefaultExceptionHandler extends ResponseEntityExceptionHandler {

  public static final String DEFAULT_ERROR_VIEW = "error";

  @ExceptionHandler(value = Exception.class)
  public final ResponseEntity<ErrorResponse> defaultErrorHandler(Exception ex, HttpServletRequest req) throws Exception {
    ResponseStatus status = AnnotationUtils.findAnnotation(ex.getClass(), ResponseStatus.class);
    ErrorResponse error = this.buildErrorResponse(ex, null, req.getRequestURL().toString());
    return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(value = CustomerNotFoundException.class)
  public final ResponseEntity<ErrorResponse> handleCustomerNotFoundException(CustomerNotFoundException ex, HttpServletRequest req) {
    ResponseStatus status = AnnotationUtils.findAnnotation(ex.getClass(), ResponseStatus.class);
    ErrorResponse error = this.buildErrorResponse(ex, status, req.getRequestURL().toString());
    return new ResponseEntity<>(error, status.value());
  }

  @ExceptionHandler(value = ExistingCustomerException.class)
  public final ResponseEntity<ErrorResponse> handleExistingCustomerException(ExistingCustomerException ex, HttpServletRequest req) {
    ResponseStatus status = AnnotationUtils.findAnnotation(ex.getClass(), ResponseStatus.class);
    ErrorResponse error = this.buildErrorResponse(ex, status, req.getRequestURL().toString());
    return new ResponseEntity<>(error, status.value());
  }

  private ErrorResponse buildErrorResponse(Throwable ex, ResponseStatus status, String request){
    String statusDesc = status != null ? status.value().toString() : "500 - Internal Error";

    return new ErrorResponse.ErrorResponseBuilder()
        .status(statusDesc)
        .timestamp(LocalDateTime.now())
        .error(ex.getClass().getSimpleName())
        .msg(ex.getMessage())
        .url(request)
        .build();
  }

}
