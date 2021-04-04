package com.builders.customer.business.impl;

import com.builders.customer.business.CustomerBusiness;
import com.builders.customer.repositories.enums.DocumentTypeEnum;
import com.builders.customer.resources.dtos.AddressDto;
import com.builders.customer.resources.dtos.CustomerDto;
import com.builders.customer.resources.dtos.DocumentDto;
import com.builders.customer.resources.dtos.PhoneDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerBusinessImpl implements CustomerBusiness {

  @Override
  public List<CustomerDto> findAll() {
    return null;
  }

  @Override
  public CustomerDto findById(long id) {
    return null;
  }

  @Override
  public List<CustomerDto> findByName(String name) {
    return null;
  }

  @Override
  public List<CustomerDto> findByZipcode(String zipcode) {
    return null;
  }

  @Override
  public List<CustomerDto> findByPhone(String number, String countryCode, String areaCode) {
    return null;
  }

  @Override
  public List<CustomerDto> findByDocument(String number, DocumentTypeEnum type) {
    return null;
  }

  @Override
  public void delete(Long id) {

  }

  @Override
  public CustomerDto create(CustomerDto customer) {
    return null;
  }

  @Override
  public CustomerDto update(Long id, CustomerDto customer) {
    return null;
  }

  @Override
  public void updateAddress(Long id, AddressDto address) {

  }

  @Override
  public void updatePhone(Long id, PhoneDto phone) {

  }

  @Override
  public void updateDocument(Long id, DocumentDto document) {

  }

  @Override
  public List<CustomerDto> findAllPaged() {
    return null;
  }
}
