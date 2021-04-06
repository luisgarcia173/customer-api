package com.builders.customer.resources.handler;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

public @Data @Builder class ErrorResponse {

  private String status;
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
  private LocalDateTime timestamp;
  private String error;
  private String msg;
  private String url;

}
