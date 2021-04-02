package com.builders.customer.repositories.entities;

import java.util.List;

public class Customer {

  private Long id;

  private String name;
  private List<Document> documents;
  private Address address;
  private List<Phone> phones;

}
