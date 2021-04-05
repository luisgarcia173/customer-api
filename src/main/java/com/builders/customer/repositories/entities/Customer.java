package com.builders.customer.repositories.entities;

import com.builders.customer.repositories.enums.StatusEnum;
import com.builders.customer.resources.dtos.CustomerDto;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
public @Data class Customer implements Serializable {

  @Id
  @GeneratedValue
  private Long id;

  private String name;
  private int age;
  @OneToMany
  private List<Document> documents;
  @OneToOne
  private Address address;
  @OneToMany
  private List<Phone> phones;
  private StatusEnum status;

}
