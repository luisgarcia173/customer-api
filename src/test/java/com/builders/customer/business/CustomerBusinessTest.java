package com.builders.customer.business;

import com.builders.customer.business.impl.CustomerBusinessImpl;
import com.builders.customer.exceptions.CustomerNotFoundException;
import com.builders.customer.exceptions.ExistingCustomerException;
import com.builders.customer.repositories.CustomerRepository;
import com.builders.customer.repositories.entities.Address;
import com.builders.customer.repositories.entities.Customer;
import com.builders.customer.repositories.entities.Document;
import com.builders.customer.repositories.entities.Phone;
import com.builders.customer.repositories.enums.DocumentTypeEnum;
import com.builders.customer.repositories.enums.PhoneTypeEnum;
import com.builders.customer.repositories.enums.StatusEnum;
import com.builders.customer.resources.dtos.AddressDto;
import com.builders.customer.resources.dtos.CustomerDto;
import com.builders.customer.resources.dtos.DocumentDto;
import com.builders.customer.resources.dtos.PhoneDto;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
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
    Address address = new Address();
    address.setZipcode("01000222");
    address.setState("SP");

    Phone phone1 = new Phone();
    phone1.setCountryCode(55);
    phone1.setAreaCode(15);
    phone1.setNumber(999998888);

    Document document = new Document();
    document.setNumber("02203304455");
    document.setType(DocumentTypeEnum.CPF);

    Customer customer1 = new Customer();
    customer1.setStatus(StatusEnum.ATIVO);
    customer1.setName("Luis Garcia");
    customer1.setAddress(address);
    customer1.setPhones(Lists.newArrayList(phone1));
    customer1.setDocuments(Lists.newArrayList(document));
    customer1.setId(1L);

    Customer customer2 = new Customer();
    customer2.setStatus(StatusEnum.ATIVO);
    customer2.setName("Luis Carlos");
    customer2.setAddress(address);
    customer2.setPhones(Lists.newArrayList(phone1));
    customer2.setDocuments(Lists.newArrayList(document));

    Customer customer3 = new Customer();
    customer3.setStatus(StatusEnum.INATIVO);
    customer3.setName("Garcia");
    customer3.setAddress(address);
    customer3.setPhones(Lists.newArrayList(phone1));
    customer3.setDocuments(Lists.newArrayList(document));

    Customer customer4 = new Customer();
    customer4.setStatus(StatusEnum.ATIVO);
    customer4.setName("Luis Garcia");
    customer4.setId(17L);

    Pageable pageable = PageRequest.of(0, 1);

    when(customerRepository.findById(1L)).thenReturn(Optional.of(customer1));
    when(customerRepository.findById(2L)).thenReturn(Optional.of(customer2));
    when(customerRepository.findById(3L)).thenReturn(Optional.of(customer3));

    when(customerRepository.findAllByStatus(StatusEnum.ATIVO)).thenReturn(Lists.newArrayList(customer1, customer2));
    when(customerRepository.findAllByStatus(StatusEnum.ATIVO, pageable)).thenReturn(Lists.newArrayList(customer1));
    when(customerRepository.findAllByNameStartsWith(anyString())).thenReturn(Lists.newArrayList(customer1, customer2));
    when(customerRepository.findAllByAddressZipcode(anyString())).thenReturn(Lists.newArrayList(customer3));
    when(customerRepository.findAllByPhonesCountryCodeAndPhonesAreaCodeAndPhonesNumber(anyInt(), anyInt(), anyLong()))
        .thenReturn(Lists.newArrayList(customer1, customer2, customer3));
    when(customerRepository.findAllByDocumentsNumberAndDocumentsType(anyString(), any()))
        .thenReturn(Lists.newArrayList(customer3));

    when(customerRepository.save(any())).thenReturn(customer1);
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
    Pageable pageable = PageRequest.of(0, 1);
    int pageSize = 1;
    int pageNumber = 0;

    // Act
    List<CustomerDto> found = customerBusiness.findAllPaged(pageSize, pageNumber);

    // Assert
    assertThat(found.size()).isEqualTo(1);
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

  @Test
  public void testFindByName() {
    // Arrange
    String name = "Luis";

    // Act
    List<CustomerDto> found = customerBusiness.findByName(name);

    // Assert
    assertThat(found.get(0).getName()).isEqualTo("Luis Garcia");
    assertThat(found.size()).isEqualTo(2);
  }

  @Test
  public void testFindByZipcode() {
    // Arrange
    String zipcode = "01000222";

    // Act
    List<CustomerDto> found = customerBusiness.findByZipcode(zipcode);

    // Assert
    assertThat(found.get(0).getName()).isEqualTo("Garcia");
    assertThat(found.size()).isEqualTo(1);
  }

  @Test
  public void testFindByPhone() {
    // Arrange
    int ddi = 55;
    int ddd = 15;
    long number = 999998888;

    // Act
    List<CustomerDto> found = customerBusiness.findByPhone(number, ddi, ddd);

    // Assert
    assertThat(found.size()).isEqualTo(3);
  }

  @Test
  public void testFindByDocument() {
    // Arrange
    DocumentTypeEnum type = DocumentTypeEnum.CPF;
    String number = "02203304455";

    // Act
    List<CustomerDto> found = customerBusiness.findByDocument(number, type);

    // Assert
    assertThat(found.get(0).getName()).isEqualTo("Garcia");
    assertThat(found.size()).isEqualTo(1);
  }

  @Test
  public void testDelete() {
    // Arrange
    Long id = 1L;

    // Act
    CustomerDto changed = customerBusiness.delete(id);

    // Assert
    assertThat(changed.getStatus()).isEqualTo(StatusEnum.INATIVO);
  }

  @Test
  public void testCreate() {
    // Arrange
    CustomerDto customer = new CustomerDto();
    customer.setName("Luis Garcia");
    customer.setAge(34);

    // Act
    CustomerDto changed = customerBusiness.create(customer);

    // Assert
    assertThat(changed.getStatus()).isEqualTo(StatusEnum.ATIVO);
  }

  @Test
  public void testUpdate() {
    // Arrange
    CustomerDto customer = new CustomerDto();
    customer.setName("Luis Garcia - ALT");
    long id = 1L;

    // Act
    CustomerDto changed = customerBusiness.update(id, customer);

    // Assert
    assertThat(changed.getId()).isEqualTo(1L);
  }

  @Test
  public void testUpdateAddress() {
    // Arrange
    AddressDto address = new AddressDto();
    address.setState("SP");
    long id = 1L;

    // Act
    customerBusiness.updateAddress(id, address);
    CustomerDto found = customerBusiness.findById(id);

    // Assert
    assertThat(found.getAddress().getState()).isEqualTo("SP");
  }

  @Test
  public void testUpdatePhoneForNew() {
    // Arrange
    PhoneDto phone = new PhoneDto();
    phone.setCountryCode(55);
    phone.setAreaCode(11);
    phone.setNumber(999887766);
    phone.setType(PhoneTypeEnum.CELLPHONE);
    long id = 1L;

    // Act
    customerBusiness.updatePhone(id, phone);
    CustomerDto found = customerBusiness.findById(id);

    // Assert
    assertThat(found.getId()).isEqualTo(1L);
    //TODO Improve assertions
  }

  @Test
  public void testUpdateExistingPhone() {
    // Arrange
    PhoneDto phone = new PhoneDto();
    phone.setCountryCode(55);
    phone.setAreaCode(15);
    phone.setNumber(999998888);
    phone.setType(PhoneTypeEnum.CELLPHONE);
    long id = 1L;

    // Act
    customerBusiness.updatePhone(id, phone);
    CustomerDto found = customerBusiness.findById(id);

    // Assert
    assertThat(found.getId()).isEqualTo(1L);
    //TODO Improve assertions
  }

  @Test
  public void testUpdateDocumentForNew() {
    // Arrange
    DocumentDto document = new DocumentDto();
    document.setNumber("12345678900");
    document.setType(DocumentTypeEnum.CPF);
    long id = 1L;

    // Act
    customerBusiness.updateDocument(id, document);
    CustomerDto found = customerBusiness.findById(id);

    // Assert
    assertThat(found.getId()).isEqualTo(1L);
    //TODO Improve assertions
  }

  @Test
  public void testUpdateExistingDocument() {
    // Arrange
    DocumentDto document = new DocumentDto();
    document.setNumber("02203304455");
    document.setType(DocumentTypeEnum.CPF);
    long id = 1L;

    // Act
    customerBusiness.updateDocument(id, document);
    CustomerDto found = customerBusiness.findById(id);

    // Assert
    assertThat(found.getId()).isEqualTo(1L);
    //TODO Improve assertions
  }

  @Test
  public void testListAllWithNoResult() {
    when(customerRepository.findAllByStatus(any())).thenReturn(null);
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
    when(customerRepository.findAllByDocumentsNumberAndDocumentsType("12345678900", DocumentTypeEnum.CPF))
        .thenReturn(Lists.newArrayList(new Customer()));
    RuntimeException exception = assertThrows(ExistingCustomerException.class, () -> customerBusiness.create(newCustomer));

    // assert
    assertThat(exception.getClass()).isEqualTo(ExistingCustomerException.class);
  }

}
