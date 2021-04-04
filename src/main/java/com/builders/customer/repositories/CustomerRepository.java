package com.builders.customer.repositories;

import com.builders.customer.repositories.entities.Customer;
import com.builders.customer.repositories.enums.DocumentTypeEnum;
import com.builders.customer.repositories.enums.StatusEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

  List<Customer> findAllByStatus(StatusEnum status);

  Page<Customer> findAll(Pageable pageable);

  List<Customer> findByNameStartsWith(String name);

  List<Customer> findAllByAddressZipcodeIn(String zipcode);

  List<Customer> findAllByPhoneCountryCodeAndPhoneAreaCodeAndPhoneNumber(String countryCode, String areaCode, String number);

  List<Customer> findAllByDocumentNumberAndDocumentType(String number, DocumentTypeEnum type);

}
