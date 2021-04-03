package com.builders.customer.resources.dtos;

import com.builders.customer.repositories.enums.PhoneTypeEnum;
import lombok.Data;

import java.io.Serializable;

public @Data class PhoneDto implements Serializable {

  private Long id;
  private int countryCode;
  private int areaCode;
  private long number;
  private String extension;
  private PhoneTypeEnum type;

}
