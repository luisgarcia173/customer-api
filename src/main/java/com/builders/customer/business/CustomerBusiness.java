package com.builders.customer.business;

import com.builders.customer.repositories.enums.DocumentTypeEnum;
import com.builders.customer.resources.dtos.AddressDto;
import com.builders.customer.resources.dtos.CustomerDto;
import com.builders.customer.resources.dtos.DocumentDto;
import com.builders.customer.resources.dtos.PhoneDto;

import java.util.List;

public interface CustomerBusiness {

  List<CustomerDto> findAll();
  CustomerDto findById(long id);
  List<CustomerDto> findByName(String name);
  List<CustomerDto> findByZipcode(String zipcode);
  List<CustomerDto> findByPhone(String number, String countryCode, String areaCode);
  List<CustomerDto> findByDocument(String number, DocumentTypeEnum type);
  void delete(Long id);
  CustomerDto create(CustomerDto customer);
  CustomerDto update(Long id, CustomerDto customer);
  void updateAddress(Long id, AddressDto address);
  void updatePhone(Long id, PhoneDto phone);
  void updateDocument(Long id, DocumentDto document);

}
