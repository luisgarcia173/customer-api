package com.builders.customer.business;

import br.com.valecard.simpleapi.business.dto.VehicleDto;
import br.com.valecard.simpleapi.business.impl.VehicleBusinessImpl;
import br.com.valecard.simpleapi.exceptions.DuplicateAttemptException;
import br.com.valecard.simpleapi.exceptions.MandatoryParametersNotFoundException;
import br.com.valecard.simpleapi.exceptions.VehicleNotFoundException;
import br.com.valecard.simpleapi.repositories.VehicleRepository;
import br.com.valecard.simpleapi.repositories.entities.Vehicle;
import br.com.valecard.simpleapi.repositories.enums.VehicleStatusEnum;
import com.builders.customer.business.impl.CustomerBusinessImpl;
import com.builders.customer.exceptions.CustomerNotFoundException;
import com.builders.customer.exceptions.ExistingCustomerException;
import com.builders.customer.repositories.CustomerRepository;
import com.builders.customer.repositories.entities.Customer;
import com.builders.customer.repositories.enums.DocumentTypeEnum;
import com.builders.customer.resources.dtos.AddressDto;
import com.builders.customer.resources.dtos.CustomerDto;
import com.builders.customer.resources.dtos.DocumentDto;
import com.builders.customer.resources.dtos.PhoneDto;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith( SpringExtension.class )
@SpringBootTest
public class CustomerBusinessTest {

  @TestConfiguration
  static class CustomerBusinessIntegrationTestContextConfiguration {
    @Bean
    public CustomerBusiness customerBusiness() {
      return new CustomerBusinessImpl();
    }
  }

  @Autowired
  private CustomerBusiness customerBusiness;

  @MockBean
  private CustomerRepository customerRepository;

  @BeforeEach
  public void setUp() {
    Customer newCustomer = new Customer();

    //TODO

    //when(customerRepository.findById(1L)).thenReturn(Optional.of(this.getCustomer()));
    //when(customerRepository.save(any())).thenReturn(newCustomer);
  }

  @Test
  public void testFindAll() {
    // Act
    List<CustomerDto> found = customerBusiness.findAll();

    // Assert
    assertThat(found.size()).isEqualTo(2);
  }

  @Test
  public void testFindAllPaged() {
    // Arrange
    Pageable pageable = null;

    // Act
    List<CustomerDto> found = customerBusiness.findAllPaged(pageable);

    // Assert
    assertThat(found.size()).isEqualTo(2);
  }

  @Test
  public void testFindById() {
    // Arrange
    Long id = 1L;

    // Act
    CustomerDto found = customerBusiness.findById(id);

    // Assert
    assertThat(found.getName()).isEqualTo("Luis Garcia");
  }

  /*List<CustomerDto> findByName(String name);
  List<CustomerDto> findByZipcode(String zipcode);
  List<CustomerDto> findByPhone(String number, String countryCode, String areaCode);
  List<CustomerDto> findByDocument(String number, DocumentTypeEnum type);
  void delete(Long id);
  CustomerDto create(CustomerDto customer);
  CustomerDto update(Long id, CustomerDto customer);
  void updateAddress(Long id, AddressDto address);
  void updatePhone(Long id, PhoneDto phone);
  void updateDocument(Long id, DocumentDto document);*/

  @Test
  public void testListAllWithNoResult() {
    when(customerRepository.findAll()).thenReturn(null);
    RuntimeException exception = assertThrows(CustomerNotFoundException.class, () -> customerBusiness.findAll());
    assertThat(exception.getClass()).isEqualTo(CustomerNotFoundException.class);
  }

  @Test
  public void testThrowExceptionWhenCustomerNotFound() {
    RuntimeException exception = assertThrows(CustomerNotFoundException.class, () -> customerBusiness.findById(99L));
    assertThat(exception.getClass()).isEqualTo(CustomerNotFoundException.class);
  }

  @Test
  public void testThrowExceptionWhenExistingEntry() {
    // arrange
    CustomerDto newCustomer = new CustomerDto();
    DocumentDto documentDto = new DocumentDto();
    documentDto.setType(DocumentTypeEnum.CPF);
    documentDto.setNumber("12345678900");
    newCustomer.setDocuments(Lists.newArrayList(documentDto));

    // act
    RuntimeException exception = assertThrows(ExistingCustomerException.class, () -> customerBusiness.create(newCustomer));

    // assert
    assertThat(exception.getClass()).isEqualTo(ExistingCustomerException.class);
  }

}