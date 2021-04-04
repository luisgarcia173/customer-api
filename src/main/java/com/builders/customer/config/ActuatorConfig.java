package com.builders.customer.config;

import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.boot.actuate.cache.CachesEndpoint;
import org.springframework.boot.actuate.metrics.MetricsEndpoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.BasicAuth;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Arrays;
import java.util.List;

@Configuration
public class ActuatorConfig {

  @Bean
  public DefaultSecurityFilterChain securityWebFilterChain(HttpSecurity http) throws Exception {
    http.requestMatcher(
        EndpointRequest.to(
            CachesEndpoint.class,
            MetricsEndpoint.class
        ))
        .authorizeRequests((requests) -> requests.anyRequest().hasRole("ENDPOINT_ADMIN"))
        .httpBasic();

    return http.build();
  }

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
