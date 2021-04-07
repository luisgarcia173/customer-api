package com.builders.customer.repositories.entities;

import com.builders.customer.repositories.enums.StatusEnum;
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
  @OneToMany(cascade = CascadeType.ALL)
  private List<Document> documents;
  @OneToOne(cascade = CascadeType.PERSIST, orphanRemoval=true)
  private Address address;
  @OneToMany(cascade = CascadeType.ALL)
  private List<Phone> phones;
  private StatusEnum status;

}
