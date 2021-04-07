package com.builders.customer.repositories.entities;

import com.builders.customer.repositories.enums.DocumentTypeEnum;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public @Data class Document implements Serializable {

  @Id
  @GeneratedValue
  private Long id;

  private String number;
  private DocumentTypeEnum type;

}
