package com.builders.customer.repositories;

import com.builders.customer.repositories.entities.Address;
import com.builders.customer.repositories.entities.Customer;
import com.builders.customer.repositories.entities.Document;
import com.builders.customer.repositories.entities.Phone;
import com.builders.customer.repositories.enums.DocumentTypeEnum;
import com.builders.customer.repositories.enums.StatusEnum;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith( SpringExtension.class )
@DataJpaTest
public class CustomerRepositoryTest {

  @Autowired
  private TestEntityManager entityManager;

  @Autowired
  private CustomerRepository customerRepository;

  @BeforeEach
  public void setUp() {
    Address address = new Address();
    address.setZipcode("01000222");
    entityManager.persist(address);
    entityManager.flush();

    Phone phone = new Phone();
    phone.setCountryCode(55);
    phone.setAreaCode(15);
    phone.setNumber(999998888);
    entityManager.persist(phone);
    entityManager.flush();

    Document document = new Document();
    document.setNumber("02203304455");
    document.setType(DocumentTypeEnum.CPF);
    entityManager.persist(document);
    entityManager.flush();

    Customer customer1 = new Customer();
    customer1.setStatus(StatusEnum.ATIVO);
    customer1.setName("Luis Carlos");
    customer1.setAddress(address);
    this.persistCustomer(customer1);

    Customer customer2 = new Customer();
    customer2.setStatus(StatusEnum.ATIVO);
    customer2.setName("Luis Garcia");
    customer2.setDocuments(Lists.newArrayList(document));
    this.persistCustomer(customer2);

    Customer customer3 = new Customer();
    customer3.setStatus(StatusEnum.INATIVO);
    customer3.setName("Garcia");
    customer3.setPhones(Lists.newArrayList(phone));
    this.persistCustomer(customer3);
  }

  private void persistCustomer(Customer customer) {
    entityManager.persist(customer);
    entityManager.flush();
  }

  @Test
  public void testFindAllByStatus() {
    // given
    StatusEnum status = StatusEnum.ATIVO;

    // when
    List<Customer> found = customerRepository.findAllByStatus(status);

    // then
    assertThat(found.size()).isEqualTo(2);
  }

  @Test
  public void testFindAllByStatusPaged() {
    // given
    StatusEnum status = StatusEnum.ATIVO;
    Pageable pageable = PageRequest.of(0, 1);

    // when
    List<Customer> found = customerRepository.findAllByStatus(status, pageable);

    // then
    assertThat(found.get(0).getName()).isEqualTo("Luis Carlos");

    // given
    pageable = pageable.next();

    // when
    found = customerRepository.findAllByStatus(status, pageable);

    // then
    assertThat(found.get(0).getName()).isEqualTo("Luis Garcia");
  }

  @Test
  public void testFindByNameStartsWith() {
    // given
    String name = "Luis";

    // when
    List<Customer> found = customerRepository.findAllByNameStartsWith(name);

    // then
    assertThat(found.size()).isEqualTo(2);
  }

  @Test
  public void testFindAllByAddressZipcode() {
    // given
    String zipcode = "01000222";

    // when
    List<Customer> found = customerRepository.findAllByAddressZipcode(zipcode);

    // then
    assertThat(found.size()).isEqualTo(1);
  }

  @Test
  public void testFindAllByPhonesCountryCodeAndPhonesAreaCodeAndPhonesNumber() {
    // given
    int ddi = 55;
    int ddd = 15;
    long number = 999998888;

    // when
    List<Customer> found = customerRepository
        .findAllByPhonesCountryCodeAndPhonesAreaCodeAndPhonesNumber(ddi, ddd, number);

    // then
    assertThat(found.get(0).getName()).isEqualTo("Garcia");
  }

  @Test
  public void testFindAllByDocumentsNumberAndDocumentsType() {
    // given
    DocumentTypeEnum type = DocumentTypeEnum.CPF;
    String number = "02203304455";

    // when
    List<Customer> found = customerRepository
        .findAllByDocumentsNumberAndDocumentsType(number, type);

    // then
    assertThat(found.get(0).getName()).isEqualTo("Luis Garcia");
  }

}
