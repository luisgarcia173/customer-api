package com.builders.customer.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
  info = @Info(
    title = "Customer API",
    version = "1.0",
    description = "Serviços que possibilitam: criar, editar, listar e atualizar os dados de clientes por completo e " +
        "também de forma granular.",
    contact = @Contact(
        name = "Luis Garcia",
        url = "https://linkedin.com/in/lncgarcia",
        email = "luisgarcia173@gmail.com"
    ),
    license = @License(
        name = "MIT Licence",
        url = "https://github.com/thombergs/code-examples/blob/master/LICENSE")
    ),
  servers = @Server(url = "http://localhost:8080")
)
public class OpenApiConfig { }
