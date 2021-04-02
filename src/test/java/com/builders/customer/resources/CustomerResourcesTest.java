package com.builders.customer.resources;

import com.builders.customer.business.CustomerBusiness;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith( SpringExtension.class )
@WebMvcTest( CustomerResources.class )
public class CustomerResourcesTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private CustomerBusiness customerBusiness;

  @BeforeAll
  public void setUp() {
//    CustomerDto vehicleChanged = this.getVehicle();
//    vehicleChanged.setPlate("ALT0000");
//    vehicleChanged.setOwnerId(2L);
//    vehicleChanged.setStatusEnum(VehicleStatusEnum.BLOQUEADO);
//
//    // Success
//    given(vehicleBusiness.findById(eq(1L))).willReturn(this.getVehicle());
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
  }

  @Test
  public void testCreate() throws Exception {
   //todo
  }

  @Test
  public void testUpdate() throws Exception {
    //todo
  }

  @Test
  public void testUpdateAddress() throws Exception {
    //todo
  }

  @Test
  public void testUpdatePhone() throws Exception {
    //todo
  }

  @Test
  public void testUpdateDocuments() throws Exception {
    //todo
  }

  @Test
  public void testFindAll() throws Exception {
    //todo
  }

  @Test
  public void testFindAllPaged() throws Exception {
    //todo
  }

  @Test
  public void testFindById() throws Exception {
    //todo
  }

  @Test
  public void testFindByName() throws Exception {
    //todo
  }

  @Test
  public void testFindByZipcode() throws Exception {
    //todo
  }

  @Test
  public void testFindByPhone() throws Exception {
    //todo
  }

  @Test
  public void testDelete() throws Exception {
    //todo
  }

}
