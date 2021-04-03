package com.builders.customer.resources;

import com.builders.customer.business.CustomerBusiness;
import com.builders.customer.repositories.enums.DocumentTypeEnum;
import com.builders.customer.resources.dtos.AddressDto;
import com.builders.customer.resources.dtos.CustomerDto;
import com.builders.customer.resources.dtos.DocumentDto;
import com.builders.customer.resources.dtos.PhoneDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/customers")
public class CustomerResources {

  @Autowired
  private CustomerBusiness customerBusiness;

  @Operation(summary = "Listar todos os clientes ativos", tags = { "cliente" })
  @ApiResponses(value = {
      @ApiResponse(
          responseCode = "200",
          description = "Clientes encontrados",
          content = { @Content(mediaType = "application/json", schema = @Schema(implementation = CustomerDto.class)) }),
      @ApiResponse(responseCode = "404", description = "Nenhum cliente encontrado", content = @Content) })
  @GetMapping("/v1.0/")
  public List<CustomerDto> findAll() {
    return this.customerBusiness.findAll();
  }

//  @Operation(summary = "Listar todos os clientes", tags = { "cliente" })
//  @ApiResponses(value = {
//      @ApiResponse(
//          responseCode = "200",
//          description = "Clientes encontrados",
//          content = { @Content(mediaType = "application/json", schema = @Schema(implementation = CustomerDto.class)) }),
//      @ApiResponse(responseCode = "404", description = "Nenhum cliente encontrado", content = @Content) })
//  @GetMapping("/")
//  public List<CustomerDto> findAllPaged() {
//    return this.customerBusiness.findAll();
//  }

  @Operation(summary = "Buscar cliente pelo Id", tags = { "cliente" })
  @ApiResponses(value = {
      @ApiResponse(
          responseCode = "200",
          description = "Cliente encontrado",
          content = { @Content(mediaType = "application/json", schema = @Schema(implementation = CustomerDto.class)) }),
      @ApiResponse(responseCode = "400", description = "Id fornecido é inválido", content = @Content),
      @ApiResponse(responseCode = "404", description = "Nenhum cliente encontrado", content = @Content) })
  @GetMapping("/v1.0/{id}")
  public CustomerDto findById(@Parameter(description = "Id do cliente", required = true) @PathVariable long id) {
    return this.customerBusiness.findById(id);
  }

  @Operation(summary = "Buscar clientes pelo nome", tags = { "cliente" })
  @ApiResponses(value = {
      @ApiResponse(
          responseCode = "200",
          description = "Clientes encontrado",
          content = { @Content(mediaType = "application/json", schema = @Schema(implementation = CustomerDto.class)) }),
      @ApiResponse(responseCode = "400", description = "Nome fornecido é inválido", content = @Content),
      @ApiResponse(responseCode = "404", description = "Nenhum cliente encontrado", content = @Content) })
  @GetMapping("/v1.0/name/{name}")
  public List<CustomerDto> findByName(@Parameter(description = "Nome do cliente", required = true) @PathVariable String name) {
    return this.customerBusiness.findByName(name);
  }

  @Operation(summary = "Buscar clientes pelo CEP", tags = { "cliente" })
  @ApiResponses(value = {
      @ApiResponse(
          responseCode = "200",
          description = "Clientes encontrado",
          content = { @Content(mediaType = "application/json", schema = @Schema(implementation = CustomerDto.class)) }),
      @ApiResponse(responseCode = "400", description = "CEP fornecido é inválido", content = @Content),
      @ApiResponse(responseCode = "404", description = "Nenhum cliente encontrado", content = @Content) })
  @GetMapping("/v1.0/address/zipcode/{zipcode}")
  public List<CustomerDto> findByZipcode(@Parameter(description = "CEP do cliente", required = true) @PathVariable String zipcode) {
    return this.customerBusiness.findByZipcode(zipcode);
  }

  @Operation(summary = "Buscar clientes pelo telefone", tags = { "cliente" })
  @ApiResponses(value = {
      @ApiResponse(
          responseCode = "200",
          description = "Clientes encontrado",
          content = { @Content(mediaType = "application/json", schema = @Schema(implementation = CustomerDto.class)) }),
      @ApiResponse(responseCode = "400", description = "Telefone fornecido é inválido", content = @Content),
      @ApiResponse(responseCode = "404", description = "Nenhum cliente encontrado", content = @Content) })
  @GetMapping("/v1.0/phone/{number}")
  public List<CustomerDto> findByPhone(
      @Parameter(description = "Número do telefone", required = true) @PathVariable String number,
      @Parameter(description = "DDI", required = false) @RequestParam String countryCode,
      @Parameter(description = "DDD", required = false) @RequestParam String areaCode) {
    return this.customerBusiness.findByPhone(number, countryCode, areaCode);
  }

  @Operation(summary = "Buscar clientes pelo número do documento", tags = { "cliente" })
  @ApiResponses(value = {
      @ApiResponse(
          responseCode = "200",
          description = "Clientes encontrado",
          content = { @Content(mediaType = "application/json", schema = @Schema(implementation = CustomerDto.class)) }),
      @ApiResponse(responseCode = "400", description = "Documento fornecido é inválido", content = @Content),
      @ApiResponse(responseCode = "404", description = "Nenhum cliente encontrado", content = @Content) })
  @GetMapping("/v1.0/document/{number}")
  public List<CustomerDto> findByDocument(
      @Parameter(description = "Número do documento", required = true) @PathVariable String number,
      @Parameter(description = "Tipo", required = false) @RequestParam DocumentTypeEnum type) {
    return this.customerBusiness.findByDocument(number, type);
  }

  @Operation(summary = "Remover cliente", tags = { "cliente" })
  @ApiResponses(value = {
      @ApiResponse(
          responseCode = "200",
          description = "Removido com sucesso",
          content = { @Content(mediaType = "application/json") }),
      @ApiResponse(responseCode = "400", description = "Id fornecido é inválido", content = @Content),
      @ApiResponse(responseCode = "404", description = "Nenhum cliente encontrado", content = @Content) })
  @DeleteMapping("/v1.0/{id}")
  public void delete(@Parameter(description = "Id do cliente", required = true) @PathVariable Long id) {
    this.customerBusiness.delete(id);
  }

  @Operation(summary = "Criar novo cliente", tags = { "cliente" })
  @ApiResponses(value = {
      @ApiResponse(
          responseCode = "200",
          description = "Cliente criado com sucesso",
          content = { @Content(mediaType = "application/json", schema = @Schema(implementation = CustomerDto.class)) })})
  @PostMapping("/v1.0/")
  //@ResponseStatus(HttpStatus.OK)
  public CustomerDto create(@Parameter(description = "Dados do cliente") @Valid @RequestBody final CustomerDto customer) {
    return this.customerBusiness.create(customer);
  }

  @Operation(summary = "Atualizar cliente", tags = { "cliente" })
  @ApiResponses(value = {
      @ApiResponse(
          responseCode = "200",
          description = "Cliente atualizado com sucesso",
          content = { @Content(mediaType = "application/json", schema = @Schema(implementation = CustomerDto.class)) }),
      @ApiResponse(responseCode = "400", description = "Id fornecido é inválido", content = @Content),
      @ApiResponse(responseCode = "404", description = "Nenhum cliente encontrado", content = @Content) })
  @PutMapping("/v1.0/{id}")
  public CustomerDto update(@Parameter(description = "Id do cliente", required = true) @PathVariable("id") final Long id,
                            @Parameter(description = "Novos atributos do cliente") @Valid @RequestBody final CustomerDto customer) {
    return this.customerBusiness.update(id, customer);
  }

  @Operation(summary = "Atualizar endereço do cliente", tags = { "cliente" })
  @ApiResponses(value = {
      @ApiResponse(
          responseCode = "200",
          description = "Endereço atualizado com sucesso",
          content = { @Content(mediaType = "application/json", schema = @Schema(implementation = AddressDto.class)) }),
      @ApiResponse(responseCode = "400", description = "Id fornecido é inválido", content = @Content),
      @ApiResponse(responseCode = "404", description = "Nenhum cliente encontrado", content = @Content) })
  @PatchMapping("/v1.0/{id}/address")
  public void updateAddress(@Parameter(description = "Id do cliente", required = true) @PathVariable("id") final Long id,
                            @Parameter(description = "Novo endereço") @Valid @RequestBody final AddressDto address) {
    this.customerBusiness.updateAddress(id, address);
  }

  @Operation(summary = "Atualizar telefone do cliente", tags = { "cliente" })
  @ApiResponses(value = {
      @ApiResponse(
          responseCode = "200",
          description = "Telefone atualizado com sucesso",
          content = { @Content(mediaType = "application/json", schema = @Schema(implementation = PhoneDto.class)) }),
      @ApiResponse(responseCode = "400", description = "Id fornecido é inválido", content = @Content),
      @ApiResponse(responseCode = "404", description = "Nenhum cliente encontrado", content = @Content) })
  @PatchMapping("/v1.0/{id}/phone")
  public void updatePhone(@Parameter(description = "Id do cliente", required = true) @PathVariable("id") final Long id,
                          @Parameter(description = "Novo telefone") @Valid @RequestBody final PhoneDto phone) {
    this.customerBusiness.updatePhone(id, phone);
  }

  @Operation(summary = "Atualizar documento do cliente", tags = { "cliente" })
  @ApiResponses(value = {
      @ApiResponse(
          responseCode = "200",
          description = "Documento atualizado com sucesso",
          content = { @Content(mediaType = "application/json", schema = @Schema(implementation = DocumentDto.class)) }),
      @ApiResponse(responseCode = "400", description = "Id fornecido é inválido", content = @Content),
      @ApiResponse(responseCode = "404", description = "Nenhum cliente encontrado", content = @Content) })
  @PatchMapping("/v1.0/{id}/document")
  public void updateDocument(@Parameter(description = "Id do cliente", required = true) @PathVariable("id") final Long id,
                             @Parameter(description = "Novos documento") @Valid @RequestBody final DocumentDto document) {
    this.customerBusiness.updateDocument(id, document);
  }

}
