package com.builders.customer.repositories.entities;

import com.builders.customer.repositories.enums.PhoneTypeEnum;

public class Phone {

  private Long id;

  private int countryCode;
  private int areaCode;
  private long number;
  private String extension;
  private PhoneTypeEnum type;

}
