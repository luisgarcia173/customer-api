package com.builders.customer.resources.dtos;

import com.builders.customer.repositories.enums.StatusEnum;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

public @Data
class CustomerDto implements Serializable {

  private Long id;

  private String name;
  private int age;
  private List<DocumentDto> documents;
  private AddressDto address;
  private List<PhoneDto> phones;
  private StatusEnum status;

}
