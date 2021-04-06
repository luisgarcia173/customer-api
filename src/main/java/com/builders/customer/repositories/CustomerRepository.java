package com.builders.customer.repositories;

import com.builders.customer.repositories.entities.Customer;
import com.builders.customer.repositories.enums.DocumentTypeEnum;
import com.builders.customer.repositories.enums.StatusEnum;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends PagingAndSortingRepository<Customer, Long> {

  List<Customer> findAllByStatus(StatusEnum status);

  List<Customer> findAllByStatus(StatusEnum status, Pageable pageable);

  List<Customer> findAllByNameStartsWith(String name);

  List<Customer> findAllByAddressZipcode(String zipcode);

  List<Customer> findAllByPhonesCountryCodeAndPhonesAreaCodeAndPhonesNumber(int countryCode, int areaCode, long number);

  List<Customer> findAllByDocumentsNumberAndDocumentsType(String number, DocumentTypeEnum type);

}
