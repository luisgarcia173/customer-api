package com.builders.customer.resources.dtos;

import com.builders.customer.repositories.enums.DocumentTypeEnum;
import lombok.Data;

import java.io.Serializable;

public @Data class DocumentDto implements Serializable {

  private Long id;
  private String number;
  private DocumentTypeEnum type;

}
