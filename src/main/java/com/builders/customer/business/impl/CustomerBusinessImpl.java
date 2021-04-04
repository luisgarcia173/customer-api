package com.builders.customer.business.impl;

import com.builders.customer.business.CustomerBusiness;
import com.builders.customer.exceptions.CustomerNotFoundException;
import com.builders.customer.repositories.CustomerRepository;
import com.builders.customer.repositories.entities.Customer;
import com.builders.customer.repositories.enums.DocumentTypeEnum;
import com.builders.customer.repositories.enums.StatusEnum;
import com.builders.customer.resources.dtos.AddressDto;
import com.builders.customer.resources.dtos.CustomerDto;
import com.builders.customer.resources.dtos.DocumentDto;
import com.builders.customer.resources.dtos.PhoneDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerBusinessImpl implements CustomerBusiness {

  @Autowired
  private ModelMapper modelMapper;

  @Autowired
  private CustomerRepository customerRepository;

  @Override
  public List<CustomerDto> findAll() {
    List<Customer> customers = this.customerRepository.findAllByStatus(StatusEnum.ATIVO);
    return this.parseListToDtoOrThrowException(customers);
  }

  @Override
  public CustomerDto findById(long id) {
    return this.entityToDto(this.findByIdOrThrowException(id));
  }

  @Override
  public List<CustomerDto> findByName(String name) {
    List<Customer> customers = this.customerRepository.findByNameStartsWith(name);
    return this.parseListToDtoOrThrowException(customers);
  }

  @Override
  public List<CustomerDto> findByZipcode(String zipcode) {
    List<Customer> customers = this.customerRepository.findAllByAddressZipcodeIn(zipcode);
    return this.parseListToDtoOrThrowException(customers);
  }

  @Override
  public List<CustomerDto> findByPhone(String number, String countryCode, String areaCode) {
    List<Customer> customers = this.customerRepository
        .findAllByPhoneCountryCodeAndPhoneAreaCodeAndPhoneNumber(countryCode, areaCode, number);
    return this.parseListToDtoOrThrowException(customers);
  }

  @Override
  public List<CustomerDto> findByDocument(String number, DocumentTypeEnum type) {
    List<Customer> customers = this.customerRepository
        .findAllByDocumentNumberAndDocumentType(number, type);
    return this.parseListToDtoOrThrowException(customers);
  }

  @Override
  public void delete(Long id) {
    Customer customer = this.findByIdOrThrowException(id);
    customer.setStatus(StatusEnum.INATIVO);
    this.customerRepository.save(customer);
  }

  @Override
  public CustomerDto create(CustomerDto customer) {
    return null;
  }

  @Override
  public CustomerDto update(Long id, CustomerDto customer) {
    Customer customerFound = this.findByIdOrThrowException(id);

    //TODO popular manualmente

    this.customerRepository.save(customerFound);
    return this.entityToDto(customerFound);
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
  public List<CustomerDto> findAllPaged(Pageable pageable) {
    return null;
  }

  private Customer dtoToEntity(CustomerDto dto) {
    Customer customer = modelMapper.map(dto, Customer.class);
    return customer;
  }

  private CustomerDto entityToDto(Customer entity) {
    CustomerDto customerDto = modelMapper.map(entity, CustomerDto.class);

    //TODO custom parametrizations

    return customerDto;
  }

  private Customer findByIdOrThrowException(Long id) {
    Customer customer = this.customerRepository.findById(id).orElse(null);
    if (customer != null) {
      return customer;
    }
    throw new CustomerNotFoundException();
  }

  private List<CustomerDto> parseListToDtoOrThrowException(List<Customer> customers) {
    if (customers != null && customers.size() > 0) {
      return customers.stream()
          .map(customer -> this.entityToDto(customer))
          .collect(Collectors.toList());
    }
    throw new CustomerNotFoundException();
  }

}
