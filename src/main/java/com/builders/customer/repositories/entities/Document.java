package com.builders.customer.repositories.entities;

import com.builders.customer.repositories.enums.DocumentTypeEnum;
import lombok.Data;

import java.io.Serializable;

public @Data class Document implements Serializable {

  private Long id;

  private String number;
  private DocumentTypeEnum type;

}
