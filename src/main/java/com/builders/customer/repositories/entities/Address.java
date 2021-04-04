package com.builders.customer.repositories.entities;

import com.builders.customer.repositories.enums.AddressTypeEnum;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
public @Data class Address implements Serializable {

  @Id
  private Long id;

  private String streetName;
  private String zipcode;
  private String number;
  private String complement;
  private String state;
  private String city;
  private AddressTypeEnum type;

}
