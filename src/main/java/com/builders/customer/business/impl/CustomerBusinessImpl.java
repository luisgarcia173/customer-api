package com.builders.customer.business.impl;

import com.builders.customer.business.CustomerBusiness;
import com.builders.customer.exceptions.CustomerNotFoundException;
import com.builders.customer.exceptions.ExistingCustomerException;
import com.builders.customer.repositories.CustomerRepository;
import com.builders.customer.repositories.entities.Address;
import com.builders.customer.repositories.entities.Customer;
import com.builders.customer.repositories.entities.Document;
import com.builders.customer.repositories.entities.Phone;
import com.builders.customer.repositories.enums.DocumentTypeEnum;
import com.builders.customer.repositories.enums.StatusEnum;
import com.builders.customer.resources.dtos.AddressDto;
import com.builders.customer.resources.dtos.CustomerDto;
import com.builders.customer.resources.dtos.DocumentDto;
import com.builders.customer.resources.dtos.PhoneDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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
    List<Customer> customers = this.customerRepository.findAllByNameStartsWith(name);
    return this.parseListToDtoOrThrowException(customers);
  }

  @Override
  public List<CustomerDto> findByZipcode(String zipcode) {
    List<Customer> customers = this.customerRepository.findAllByAddressZipcode(zipcode);
    return this.parseListToDtoOrThrowException(customers);
  }

  @Override
  public List<CustomerDto> findByPhone(long number, int countryCode, int areaCode) {
    List<Customer> customers = this.customerRepository
        .findAllByPhonesCountryCodeAndPhonesAreaCodeAndPhonesNumber(countryCode, areaCode, number);
    return this.parseListToDtoOrThrowException(customers);
  }

  @Override
  public List<CustomerDto> findByDocument(String number, DocumentTypeEnum type) {
    List<Customer> customers = this.customerRepository
        .findAllByDocumentsNumberAndDocumentsType(number, type);
    return this.parseListToDtoOrThrowException(customers);
  }

  @Override
  public CustomerDto delete(Long id) {
    Customer customer = this.findByIdOrThrowException(id);
    customer.setStatus(StatusEnum.INATIVO);
    this.customerRepository.save(customer);
    return this.entityToDto(customer);
  }

  @Override
  public CustomerDto create(CustomerDto customer) {
    List<DocumentDto> documents = customer.getDocuments();
    if (documents != null) {
      for (DocumentDto doc : documents) {
        List<CustomerDto> found = this.findByDocument(doc.getNumber(), doc.getType());
        if (found != null && !found.isEmpty()) {
          throw new ExistingCustomerException();
        }
      }
    }

    Customer customerToPersist = this.dtoToEntity(customer);
    customerToPersist.setStatus(StatusEnum.ATIVO);
    this.customerRepository.save(customerToPersist);

    return this.entityToDto(customerToPersist);
  }

  @Override
  @Transactional
  public CustomerDto update(Long id, CustomerDto customer) {
    Customer customerFound = this.findByIdOrThrowException(id);

    customerFound.setName(customer.getName());
    customerFound.setAge(customer.getAge());

    Address address = this.modelMapper.map(customer.getAddress(), Address.class);
    customerFound.setAddress(address);

    List<Phone> phones = new ArrayList<>();
    if (customer.getPhones() != null && customer.getPhones().size() > 0) {
      for (PhoneDto phone: customer.getPhones()) {
        phones.add(this.modelMapper.map(phone, Phone.class));
      }
    }
    customerFound.setPhones(phones);

    List<Document> documents = new ArrayList<>();
    if (customer.getDocuments() != null && customer.getDocuments().size() > 0) {
      for (DocumentDto document: customer.getDocuments()) {
        documents.add(this.modelMapper.map(document, Document.class));
      }
    }
    customerFound.setDocuments(documents);

    this.customerRepository.save(customerFound);
    return this.entityToDto(customerFound);
  }

  @Override
  public void updateAddress(Long id, AddressDto address) {
    Customer customerFound = this.findByIdOrThrowException(id);
    Address newAddress = this.modelMapper.map(address, Address.class);
    customerFound.setAddress(newAddress);
    this.customerRepository.save(customerFound);
  }

  @Override
  public void updatePhone(Long id, PhoneDto phone) {
    Customer customerFound = this.findByIdOrThrowException(id);

    List<Phone> phones = customerFound.getPhones();
    boolean isNew = true;
    for (Phone p : phones) {
      if (p.getAreaCode() == phone.getAreaCode() &&
          p.getCountryCode() == phone.getCountryCode() &&
          p.getNumber() == phone.getNumber()) {
        p.setExtension(phone.getExtension());
        p.setType(phone.getType());
        isNew = false;
        break;
      }
    }
    if (isNew) {
      Phone p = this.modelMapper.map(phone, Phone.class);
      if (phones != null) {
        phones.add(p);
      } else {
        customerFound.setPhones(List.of(p));
      }
    }

    this.customerRepository.save(customerFound);
  }

  @Override
  public void updateDocument(Long id, DocumentDto document) {
    Customer customerFound = this.findByIdOrThrowException(id);

    List<Document> documents = customerFound.getDocuments();
    boolean isNew = true;
    for (Document d : documents) {
      if (d.getNumber().equals(document.getNumber())) {
        d.setType(document.getType());
        isNew = false;
        break;
      }
    }
    if (isNew) {
      Document d = this.modelMapper.map(document, Document.class);
      if (documents != null) {
        documents.add(d);
      } else {
        customerFound.setDocuments(List.of(d));
      }
    }

    this.customerRepository.save(customerFound);
  }

  @Override
  public List<CustomerDto> findAllPaged(int pageSize, int pageNumber) {
    Pageable pageable = PageRequest.of(pageNumber, pageSize);
    List<Customer> customers = this.customerRepository.findAllByStatus(StatusEnum.ATIVO, pageable);
    return this.parseListToDtoOrThrowException(customers);
  }

  private Customer dtoToEntity(CustomerDto dto) {
    Customer customer = this.modelMapper.map(dto, Customer.class);
    if (dto.getAddress() != null) {
      customer.setAddress(this.modelMapper.map(dto.getAddress(), Address.class));
    }
    if (dto.getPhones() != null && dto.getPhones().size() > 0) {
      List<Phone> phones = new ArrayList<>();
      for(PhoneDto phoneDto : dto.getPhones()){
        phones.add(this.modelMapper.map(phoneDto, Phone.class));
      }
      customer.setPhones(phones);
    }
    if (dto.getDocuments() != null && dto.getDocuments().size() > 0) {
      List<Document> documents = new ArrayList<>();
      for(DocumentDto documentDto : dto.getDocuments()){
        documents.add(this.modelMapper.map(documentDto, Document.class));
      }
      customer.setDocuments(documents);
    }
    return customer;
  }

  private CustomerDto entityToDto(Customer entity) {
    //TODO custom parametrizations
    return this.modelMapper.map(entity, CustomerDto.class);
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
          .map(this::entityToDto)
          .collect(Collectors.toList());
    }
    throw new CustomerNotFoundException();
  }

}
