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
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
public class CustomerResources {

  @Autowired
  private CustomerBusiness customerBusiness;

  @Operation(summary = "Listar todos os clientes ativos")
  @ApiResponses(value = {
      @ApiResponse(
          responseCode = "200",
          description = "Clientes encontrados",
          content = { @Content(mediaType = "application/json", schema = @Schema(implementation = CustomerDto.class)) }),
      @ApiResponse(responseCode = "404", description = "Nenhum cliente encontrado", content = @Content) })
  @GetMapping(produces = "application/vnd.customer.app-v1.0+json")
  @Deprecated
  public List<CustomerDto> findAll() {
    return this.customerBusiness.findAll();
  }

  @Operation(summary = "Listar todos os clientes ativos de forma paginada")
  @ApiResponses(value = {
      @ApiResponse(
          responseCode = "200",
          description = "Clientes encontrados",
          content = { @Content(mediaType = "application/json", schema = @Schema(implementation = CustomerDto.class)) }),
      @ApiResponse(responseCode = "404", description = "Nenhum cliente encontrado", content = @Content) })
  @GetMapping(produces = {
      "application/vnd.customer.app-v1.1+json",
      "application/vnd.customer.app-v1.2+json"
  })
  public List<CustomerDto> findAllPaged(
      @Parameter(description = "pageSize", required = true) @RequestParam int pageSize,
      @Parameter(description = "pageNumber", required = true) @RequestParam int pageNumber) {
    return this.customerBusiness.findAllPaged(pageSize, pageNumber);
  }

  @Operation(summary = "Buscar cliente pelo Id")
  @ApiResponses(value = {
      @ApiResponse(
          responseCode = "200",
          description = "Cliente encontrado",
          content = { @Content(mediaType = "application/json", schema = @Schema(implementation = CustomerDto.class)) }),
      @ApiResponse(responseCode = "400", description = "Id fornecido é inválido", content = @Content),
      @ApiResponse(responseCode = "404", description = "Nenhum cliente encontrado", content = @Content) })
  @GetMapping(value = "/{id}", produces = {
      "application/vnd.customer.app-v1.0+json",
      "application/vnd.customer.app-v1.1+json",
      "application/vnd.customer.app-v1.2+json"
  })
  public CustomerDto findById(@Parameter(description = "Id do cliente", required = true) @PathVariable long id) {
    return this.customerBusiness.findById(id);
  }

  @Operation(summary = "Buscar clientes pelo nome")
  @ApiResponses(value = {
      @ApiResponse(
          responseCode = "200",
          description = "Clientes encontrado",
          content = { @Content(mediaType = "application/json", schema = @Schema(implementation = CustomerDto.class)) }),
      @ApiResponse(responseCode = "400", description = "Nome fornecido é inválido", content = @Content),
      @ApiResponse(responseCode = "404", description = "Nenhum cliente encontrado", content = @Content) })
  @GetMapping(value = "/name/{name}", produces = {
      "application/vnd.customer.app-v1.1+json",
      "application/vnd.customer.app-v1.2+json"
  })
  public List<CustomerDto> findByName(@Parameter(description = "Nome do cliente", required = true) @PathVariable String name) {
    return this.customerBusiness.findByName(name);
  }

  @Operation(summary = "Buscar clientes pelo CEP")
  @ApiResponses(value = {
      @ApiResponse(
          responseCode = "200",
          description = "Clientes encontrado",
          content = { @Content(mediaType = "application/json", schema = @Schema(implementation = CustomerDto.class)) }),
      @ApiResponse(responseCode = "400", description = "CEP fornecido é inválido", content = @Content),
      @ApiResponse(responseCode = "404", description = "Nenhum cliente encontrado", content = @Content) })
  @GetMapping(value = "/address/zipcode/{zipcode}", produces = "application/vnd.customer.app-v1.2+json")
  public List<CustomerDto> findByZipcode(@Parameter(description = "CEP do cliente", required = true) @PathVariable String zipcode) {
    return this.customerBusiness.findByZipcode(zipcode);
  }

  @Operation(summary = "Buscar clientes pelo telefone")
  @ApiResponses(value = {
      @ApiResponse(
          responseCode = "200",
          description = "Clientes encontrado",
          content = { @Content(mediaType = "application/json", schema = @Schema(implementation = CustomerDto.class)) }),
      @ApiResponse(responseCode = "400", description = "Telefone fornecido é inválido", content = @Content),
      @ApiResponse(responseCode = "404", description = "Nenhum cliente encontrado", content = @Content) })
  @GetMapping(value = "/phone/{number}", produces = "application/vnd.customer.app-v1.2+json")
  public List<CustomerDto> findByPhone(
      @Parameter(description = "Número do telefone", required = true) @PathVariable long number,
      @Parameter(description = "DDI", required = true) @RequestParam int countryCode,
      @Parameter(description = "DDD", required = true) @RequestParam int areaCode) {
    return this.customerBusiness.findByPhone(number, countryCode, areaCode);
  }

  @Operation(summary = "Buscar clientes pelo número do documento")
  @ApiResponses(value = {
      @ApiResponse(
          responseCode = "200",
          description = "Clientes encontrado",
          content = { @Content(mediaType = "application/json", schema = @Schema(implementation = CustomerDto.class)) }),
      @ApiResponse(responseCode = "400", description = "Documento fornecido é inválido", content = @Content),
      @ApiResponse(responseCode = "404", description = "Nenhum cliente encontrado", content = @Content) })
  @GetMapping(value = "/document/{number}", produces = {
      "application/vnd.customer.app-v1.1+json",
      "application/vnd.customer.app-v1.2+json"
  })
  public List<CustomerDto> findByDocument(
      @Parameter(description = "Número do documento", required = true) @PathVariable String number,
      @Parameter(description = "Tipo", required = true) @RequestParam DocumentTypeEnum type) {
    return this.customerBusiness.findByDocument(number, type);
  }

  @Operation(summary = "Remover cliente")
  @ApiResponses(value = {
      @ApiResponse(
          responseCode = "200",
          description = "Removido com sucesso",
          content = { @Content(mediaType = "application/json") }),
      @ApiResponse(responseCode = "400", description = "Id fornecido é inválido", content = @Content),
      @ApiResponse(responseCode = "404", description = "Nenhum cliente encontrado", content = @Content) })
  @DeleteMapping(value = "/{id}", produces = {
      "application/vnd.customer.app-v1.0+json",
      "application/vnd.customer.app-v1.1+json",
      "application/vnd.customer.app-v1.2+json"
  })
  public void delete(@Parameter(description = "Id do cliente", required = true) @PathVariable Long id) {
    this.customerBusiness.delete(id);
  }

  @Operation(summary = "Criar novo cliente")
  @ApiResponses(value = {
      @ApiResponse(
          responseCode = "200",
          description = "Cliente criado com sucesso",
          content = { @Content(mediaType = "application/json", schema = @Schema(implementation = CustomerDto.class)) })})
  @PostMapping(produces = {
      "application/vnd.customer.app-v1.0+json",
      "application/vnd.customer.app-v1.1+json",
      "application/vnd.customer.app-v1.2+json"
  })
  //@ResponseStatus(HttpStatus.OK)
  public CustomerDto create(@Parameter(description = "Dados do cliente") @RequestBody final CustomerDto customer) {
    return this.customerBusiness.create(customer);
  }

  @Operation(summary = "Atualizar cliente")
  @ApiResponses(value = {
      @ApiResponse(
          responseCode = "200",
          description = "Cliente atualizado com sucesso",
          content = { @Content(mediaType = "application/json", schema = @Schema(implementation = CustomerDto.class)) }),
      @ApiResponse(responseCode = "400", description = "Id fornecido é inválido", content = @Content),
      @ApiResponse(responseCode = "404", description = "Nenhum cliente encontrado", content = @Content) })
  @PutMapping(value = "/{id}", produces = {
      "application/vnd.customer.app-v1.0+json",
      "application/vnd.customer.app-v1.1+json",
      "application/vnd.customer.app-v1.2+json"
  })
  public CustomerDto update(@Parameter(description = "Id do cliente", required = true) @PathVariable("id") final Long id,
                            @Parameter(description = "Novos atributos do cliente") @RequestBody final CustomerDto customer) {
    return this.customerBusiness.update(id, customer);
  }

  @Operation(summary = "Atualizar endereço do cliente")
  @ApiResponses(value = {
      @ApiResponse(
          responseCode = "200",
          description = "Endereço atualizado com sucesso",
          content = { @Content(mediaType = "application/json", schema = @Schema(implementation = AddressDto.class)) }),
      @ApiResponse(responseCode = "400", description = "Id fornecido é inválido", content = @Content),
      @ApiResponse(responseCode = "404", description = "Nenhum cliente encontrado", content = @Content) })
  @PatchMapping(value = "/{id}/address", produces = {
      "application/vnd.customer.app-v1.1+json",
      "application/vnd.customer.app-v1.2+json"
  })
  public void updateAddress(@Parameter(description = "Id do cliente", required = true) @PathVariable("id") final Long id,
                            @Parameter(description = "Novo endereço") @RequestBody final AddressDto address) {
    this.customerBusiness.updateAddress(id, address);
  }

  @Operation(summary = "Atualizar telefone do cliente")
  @ApiResponses(value = {
      @ApiResponse(
          responseCode = "200",
          description = "Telefone atualizado com sucesso",
          content = { @Content(mediaType = "application/json", schema = @Schema(implementation = PhoneDto.class)) }),
      @ApiResponse(responseCode = "400", description = "Id fornecido é inválido", content = @Content),
      @ApiResponse(responseCode = "404", description = "Nenhum cliente encontrado", content = @Content) })
  @PatchMapping(value = "/{id}/phone", produces = {
      "application/vnd.customer.app-v1.1+json",
      "application/vnd.customer.app-v1.2+json"
  })
  public void updatePhone(@Parameter(description = "Id do cliente", required = true) @PathVariable("id") final Long id,
                          @Parameter(description = "Novo telefone") @RequestBody final PhoneDto phone) {
    this.customerBusiness.updatePhone(id, phone);
  }

  @Operation(summary = "Atualizar documento do cliente")
  @ApiResponses(value = {
      @ApiResponse(
          responseCode = "200",
          description = "Documento atualizado com sucesso",
          content = { @Content(mediaType = "application/json", schema = @Schema(implementation = DocumentDto.class)) }),
      @ApiResponse(responseCode = "400", description = "Id fornecido é inválido", content = @Content),
      @ApiResponse(responseCode = "404", description = "Nenhum cliente encontrado", content = @Content) })
  @PatchMapping(value = "/{id}/document", produces = {
      "application/vnd.customer.app-v1.1+json",
      "application/vnd.customer.app-v1.2+json"
  })
  public void updateDocument(@Parameter(description = "Id do cliente", required = true) @PathVariable("id") final Long id,
                             @Parameter(description = "Novos documento") @RequestBody final DocumentDto document) {
    this.customerBusiness.updateDocument(id, document);
  }

}
