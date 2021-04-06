package com.builders.customer.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.DocExpansion;
import springfox.documentation.swagger.web.UiConfiguration;
import springfox.documentation.swagger.web.UiConfigurationBuilder;

import java.util.Collections;

@Configuration
public class SwaggerConfig {

  private static final String VERSION_1_0 = "1.0";
  private static final String VERSION_1_1 = "1.1";
  private static final String VERSION_1_2 = "1.2";

  @Bean
  public Docket swaggerCustomerApi10() {
    return this.getDocket(VERSION_1_0);
  }

  @Bean
  public Docket swaggerCustomerApi11() {
    return this.getDocket(VERSION_1_1);
  }

  @Bean
  public Docket swaggerCustomerApi12() {
    return this.getDocket(VERSION_1_2);
  }

  @Bean
  public UiConfiguration uiConfig() {
    return UiConfigurationBuilder.builder()
        .docExpansion(DocExpansion.LIST) // or DocExpansion.NONE or DocExpansion.FULL
        .build();
  }

  private Docket getDocket(String version) {
    return new Docket(DocumentationType.OAS_30)
        .host("localhost:8080")
        .groupName("customer-api-"+version)
        .select()
        .apis(p -> {
          if (p.produces() != null) {
            for (MediaType mt : p.produces()) {
              if (mt.toString().equals("application/vnd.customer.app-v"+version+"+json")) {
                return true;
              }
            }
          }
          return false;
        })
        .build()
        .produces(Collections.singleton("application/vnd.customer.app-v"+version+"+json"))
        .apiInfo(this.getApiInfo(version));
  }

  private ApiInfo getApiInfo(String version) {
    return new ApiInfoBuilder()
        .version(version)
        .title("Customer API")
        .description("Documentação da Customer API v"+version+". Serviços que possibilitam: criar, editar, listar e " +
            "atualizar os dados de clientes por completo e também de forma granular.")
        .contact(new Contact(
            "Luis Garcia",
            "https://linkedin.com/in/lncgarcia",
            "luisgarcia173@gmail.com")
        )
        .license("Apache 2.0")
        .licenseUrl("http://www.apache.org/licenses/LICENSE-2.0.html")
        .build();
  }

}
