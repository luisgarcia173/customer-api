package com.builders.customer.repositories;

import com.builders.customer.repositories.entities.Customer;
import com.builders.customer.repositories.enums.StatusEnum;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
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

  @BeforeAll
  public void setUp() {
    Customer customer = new Customer();

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

  }

  @Test
  public void testFindByNameStartsWith() {

  }

  @Test
  public void testFindAllByAddressZipcode() {

  }

  @Test
  public void testFindAllByPhonesCountryCodeAndPhonesAreaCodeAndPhonesNumber() {

  }

  @Test
  public void testFindAllByDocumentsNumberAndDocumentsType() {

  }

}
