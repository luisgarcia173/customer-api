package com.builders.customer.repositories.entities;

import com.builders.customer.repositories.enums.AddressTypeEnum;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public @Data class Address implements Serializable {

  @Id
  @GeneratedValue
  private Long id;

  private String streetName;
  private String zipcode;
  private String number;
  private String complement;
  private String state;
  private String city;
  private AddressTypeEnum type;

}
