package com.builders.customer.resources;

import com.builders.customer.business.CustomerBusiness;
import com.builders.customer.repositories.entities.Address;
import com.builders.customer.repositories.enums.AddressTypeEnum;
import com.builders.customer.repositories.enums.DocumentTypeEnum;
import com.builders.customer.repositories.enums.PhoneTypeEnum;
import com.builders.customer.repositories.enums.StatusEnum;
import com.builders.customer.resources.dtos.AddressDto;
import com.builders.customer.resources.dtos.CustomerDto;
import com.builders.customer.resources.dtos.DocumentDto;
import com.builders.customer.resources.dtos.PhoneDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith( SpringExtension.class )
@WebMvcTest( CustomerResources.class )
public class CustomerResourcesTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private CustomerBusiness customerBusiness;

  @BeforeEach
  public void setUp() {
//    CustomerDto vehicleChanged = this.getVehicle();
//    vehicleChanged.setPlate("ALT0000");
//    vehicleChanged.setOwnerId(2L);
//    vehicleChanged.setStatusEnum(VehicleStatusEnum.BLOQUEADO);
//
//
//    given(vehicleBusiness.update(anyLong(), any())).willReturn(vehicleChanged);
//    given(vehicleBusiness.transferOwner(eq(1L), eq(2L), any())).willReturn(vehicleChanged);
//    given(vehicleBusiness.updateStatus(eq(1L), any())).willReturn(vehicleChanged);
//    doNothing().when(vehicleBusiness).delete(eq(1L));
//    given(vehicleBusiness.create(any())).willReturn(this.getVehicle());
//    // Error
//    given(vehicleBusiness.findById(eq(2L))).willThrow(VehicleNotFoundException.class);
//    doThrow(VehicleNotFoundException.class).when(vehicleBusiness).delete(eq(2L));
//    // List
//    given(vehicleBusiness.listAll()).willReturn(List.of(new VehicleDto(), new VehicleDto()));

    AddressDto address = new AddressDto();
    address.setState("SP");

    PhoneDto phone = new PhoneDto();
    phone.setType(PhoneTypeEnum.CELLPHONE);

    DocumentDto document = new DocumentDto();
    document.setType(DocumentTypeEnum.CPF);

    CustomerDto customer1 = new CustomerDto();
    customer1.setName("Luis Garcia");
    customer1.setAddress(address);
    customer1.setDocuments(Lists.newArrayList(document));
    customer1.setId(1L);
    customer1.setStatus(StatusEnum.ATIVO);

    CustomerDto customer2 = new CustomerDto();
    customer2.setName("Luis Carlos");
    customer2.setPhones(Lists.newArrayList(phone));

    CustomerDto customer3 = new CustomerDto();
    customer3.setName("Luis Garcia");
    customer3.setAddress(address);
    customer3.setDocuments(Lists.newArrayList(document));
    customer3.setId(1L);
    customer3.setStatus(StatusEnum.INATIVO);

    CustomerDto customer4 = new CustomerDto();
    customer4.setId(1L);
    customer4.setName("Luis Garcia - ALT");

    // Success
    given(customerBusiness.findById(eq(1L))).willReturn(customer1);
    given(customerBusiness.delete(eq(1L))).willReturn(customer3);
    given(customerBusiness.create(any())).willReturn(customer1);
    given(customerBusiness.update(anyLong(), any())).willReturn(customer4);
    doNothing().when(customerBusiness).updateAddress(anyLong(), any());
    doNothing().when(customerBusiness).updatePhone(anyLong(), any());
    doNothing().when(customerBusiness).updateDocument(anyLong(), any());

    // List
    given(customerBusiness.findAll()).willReturn(List.of(new CustomerDto(), new CustomerDto()));
    given(customerBusiness.findAllPaged(anyInt(), anyInt())).willReturn(List.of(new CustomerDto()));
    given(customerBusiness.findByName(anyString())).willReturn(List.of(customer1, customer2));
    given(customerBusiness.findByZipcode(anyString())).willReturn(List.of(customer1));
    given(customerBusiness.findByPhone(anyLong(), anyInt(), anyInt())).willReturn(List.of(customer2));
    given(customerBusiness.findByDocument(anyString(), any())).willReturn(List.of(customer1));
  }

  @Test
  public void testFindAll() throws Exception {
    mockMvc.perform(
        get("/api/customers/")
            .contentType(MediaType.APPLICATION_JSON)
            .accept("application/vnd.customer.app-v1.0+json"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(2)));
  }

  @Test
  public void testFindAllWithWrongApiVersion() throws Exception {
    mockMvc.perform(
        get("/api/customers/")
            .contentType(MediaType.APPLICATION_JSON)
            .accept("application/vnd.customer.app-v1.1+json"))
        .andExpect(status().isBadRequest());
  }

  @Test
  public void testFindAllPaged() throws Exception {
    mockMvc.perform(
        get("/api/customers?pageSize=1&pageNumber=0")
            .contentType(MediaType.APPLICATION_JSON)
            .accept("application/vnd.customer.app-v1.1+json"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(1)));
  }

  @Test
  public void testFindById() throws Exception {
    mockMvc.perform(
        get("/api/customers/1")
            .contentType(MediaType.APPLICATION_JSON)
            .accept("application/vnd.customer.app-v1.0+json"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.name", is("Luis Garcia")));
  }

  @Test
  public void testFindByName() throws Exception {
    mockMvc.perform(
        get("/api/customers/name/Luis")
            .contentType(MediaType.APPLICATION_JSON)
            .accept("application/vnd.customer.app-v1.1+json"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(2)))
        .andExpect(jsonPath("$[0].name", is("Luis Garcia")))
        .andExpect(jsonPath("$[1].name", is("Luis Carlos")));
  }

  @Test
  public void testFindByZipcode() throws Exception {
    mockMvc.perform(
        get("/api/customers/address/zipcode/01110222")
            .contentType(MediaType.APPLICATION_JSON)
            .accept("application/vnd.customer.app-v1.2+json"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(1)))
        .andExpect(jsonPath("$[0].address.state", is("SP")));
  }

  @Test
  public void testFindByPhone() throws Exception {
    mockMvc.perform(
        get("/api/customers/phone/999998888?countryCode=55&areaCode=15")
            .contentType(MediaType.APPLICATION_JSON)
            .accept("application/vnd.customer.app-v1.2+json"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(1)))
        .andExpect(jsonPath("$[0].phones[0].type", is(PhoneTypeEnum.CELLPHONE.toString())));
  }

  @Test
  public void testFindByDocument() throws Exception {
    mockMvc.perform(
        get("/api/customers/document/33344455566?type=CPF")
            .contentType(MediaType.APPLICATION_JSON)
            .accept("application/vnd.customer.app-v1.2+json"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(1)))
        .andExpect(jsonPath("$[0].documents[0].type", is(DocumentTypeEnum.CPF.toString())));
  }

  @Test
  public void testDelete() throws Exception {
    mockMvc.perform(
        delete("/api/customers/1")
            .contentType(MediaType.APPLICATION_JSON)
            .accept("application/vnd.customer.app-v1.0+json"))
        .andExpect(status().isOk());
  }

  @Test
  public void testCreate() throws Exception {
    CustomerDto customer = new CustomerDto();
    customer.setName("Luis Garcia");
    String payload = this.getCustomerDtoAsJson(customer);

    mockMvc.perform(
        post("/api/customers/")
            .contentType(MediaType.APPLICATION_JSON)
            .characterEncoding("UTF-8")
            .accept("application/vnd.customer.app-v1.0+json")
            .content(payload))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id", is(1)))
        .andExpect(jsonPath("$.status", is(StatusEnum.ATIVO.toString())));
  }

  @Test
  public void testUpdate() throws Exception {
    CustomerDto customer = new CustomerDto();
    customer.setName("Luis Garcia - ALT");
    String payload = this.getCustomerDtoAsJson(customer);

    mockMvc.perform(
        put("/api/customers/1")
            .contentType(MediaType.APPLICATION_JSON)
            .characterEncoding("UTF-8")
            .accept("application/vnd.customer.app-v1.0+json")
            .content(payload))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id", is(1)))
        .andExpect(jsonPath("$.name", is("Luis Garcia - ALT")));
  }

  @Test
  public void testUpdateAddress() throws Exception {
    AddressDto address = new AddressDto();
    address.setState("RJ");
    String payload = new ObjectMapper().writeValueAsString(address);

    mockMvc.perform(
        patch("/api/customers/1/address")
            .contentType(MediaType.APPLICATION_JSON)
            .characterEncoding("UTF-8")
            .accept("application/vnd.customer.app-v1.2+json")
            .content(payload))
        .andExpect(status().isOk());
  }

  @Test
  public void testUpdatePhone() throws Exception {
    PhoneDto phone = new PhoneDto();
    phone.setType(PhoneTypeEnum.CELLPHONE);
    String payload = new ObjectMapper().writeValueAsString(phone);

    mockMvc.perform(
        patch("/api/customers/1/phone")
            .contentType(MediaType.APPLICATION_JSON)
            .characterEncoding("UTF-8")
            .accept("application/vnd.customer.app-v1.2+json")
            .content(payload))
        .andExpect(status().isOk());
  }

  @Test
  public void testUpdateDocument() throws Exception {
    DocumentDto document = new DocumentDto();
    document.setType(DocumentTypeEnum.CPF);
    String payload = new ObjectMapper().writeValueAsString(document);

    mockMvc.perform(
        patch("/api/customers/1/document")
            .contentType(MediaType.APPLICATION_JSON)
            .characterEncoding("UTF-8")
            .accept("application/vnd.customer.app-v1.2+json")
            .content(payload))
        .andExpect(status().isOk());
  }

  private String getCustomerDtoAsJson(CustomerDto dto) throws JsonProcessingException {
    return new ObjectMapper().writeValueAsString(dto);
  }

  private CustomerDto getCustomerDto() {
    CustomerDto customer = new CustomerDto();

    customer.setId(10101L);
    customer.setStatus(StatusEnum.ATIVO);
    customer.setAge(34);
    customer.setName("Luis Garcia");
    customer.setPhones(this.getListPhoneDto());
    customer.setDocuments(this.getListDocumentDto());
    customer.setAddress(this.getAddressDto());

    return customer;
  }

  private AddressDto getAddressDto() {
    AddressDto address = new AddressDto();

    address.setId(1L);
    address.setCity("Sorocaba");
    address.setState("SP");
    address.setComplement("N/A");
    address.setNumber("40A");
    address.setType(AddressTypeEnum.APARTMENT);
    address.setZipcode("01010100");
    address.setStreetName("Rua Principal");

    return address;
  }

  private List<PhoneDto> getListPhoneDto() {

    PhoneDto phone1 = new PhoneDto();
    phone1.setId(1L);
    phone1.setCountryCode(55);
    phone1.setAreaCode(15);
    phone1.setNumber(999998888);
    phone1.setType(PhoneTypeEnum.CELLPHONE);

    PhoneDto phone2 = new PhoneDto();
    phone2.setId(2L);
    phone2.setCountryCode(55);
    phone2.setAreaCode(15);
    phone2.setNumber(33334444);
    phone2.setType(PhoneTypeEnum.BUSINESS);

    return Lists.newArrayList(phone1, phone2);
  }

  private List<DocumentDto> getListDocumentDto() {

    DocumentDto document1 = new DocumentDto();
    document1.setId(1L);
    document1.setNumber("11122233344");
    document1.setType(DocumentTypeEnum.CPF);

    DocumentDto document2 = new DocumentDto();
    document2.setId(2L);
    document2.setNumber("112223334");
    document2.setType(DocumentTypeEnum.RG);

    return Lists.newArrayList(document1, document2);
  }

}
