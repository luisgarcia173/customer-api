package com.builders.customer.repositories.entities;

import com.builders.customer.repositories.enums.AddressTypeEnum;
import lombok.Data;

import java.io.Serializable;

public @Data class Address implements Serializable {

  private Long id;

  private String streetName;
  private String zipcode;
  private String number;
  private String complement;
  private String state;
  private String city;
  private AddressTypeEnum type;

}
