package com.builders.customer.repositories.entities;

import com.builders.customer.repositories.enums.PhoneTypeEnum;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
public @Data class Phone implements Serializable {

  @Id
  @GeneratedValue
  private Long id;

  private int countryCode;
  private int areaCode;
  private long number;
  private String extension;
  private PhoneTypeEnum type;

}
