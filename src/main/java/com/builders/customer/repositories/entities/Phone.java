package com.builders.customer.repositories.entities;

import com.builders.customer.repositories.enums.PhoneTypeEnum;
import lombok.Data;

import java.io.Serializable;

public @Data class Phone implements Serializable {

  private Long id;

  private int countryCode;
  private int areaCode;
  private long number;
  private String extension;
  private PhoneTypeEnum type;

}
