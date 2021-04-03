package com.builders.customer.repositories.entities;

import com.builders.customer.repositories.enums.StatusEnum;
import com.builders.customer.resources.dtos.CustomerDto;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

public @Data class Customer implements Serializable {

  private Long id;

  private String name;
  private int age;
  private List<Document> documents;
  private Address address;
  private List<Phone> phones;
  private StatusEnum status;

}
