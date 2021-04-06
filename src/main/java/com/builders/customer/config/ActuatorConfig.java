package com.builders.customer.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class ActuatorConfig {

  @Bean
  public Docket actuator() {
    return new Docket(DocumentationType.OAS_30)
        .groupName("health-check")
        .select()
        .apis(RequestHandlerSelectors.any())
        .paths(PathSelectors.regex("/actuator.*"))
        .build()
        .apiInfo(new ApiInfoBuilder()
          .title("Actuator API")
          .description("Documentação do Actuator API. Serviços que possibilitam a análise da saúde da aplicação" +
              " como status, consumo memória, cache, logs etc.")
          .build());
  }

}
