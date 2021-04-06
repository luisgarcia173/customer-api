package com.builders.customer.config;

import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.boot.actuate.cache.CachesEndpoint;
import org.springframework.boot.actuate.metrics.MetricsEndpoint;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.cors()
        .and()
        .authorizeRequests()
        .antMatchers(HttpMethod.GET, "/api/customers/**").permitAll()
        .antMatchers(HttpMethod.POST, "/api/customers/**").permitAll()
        .antMatchers(HttpMethod.PUT, "/api/customers/**").permitAll()
        .antMatchers(HttpMethod.PATCH, "/api/customers/**").permitAll()
        .antMatchers(HttpMethod.DELETE, "/api/customers/**").permitAll()
        .and()
        .csrf().disable()
        .formLogin().disable();

    http.requestMatcher(
        EndpointRequest.to(
            CachesEndpoint.class,
            MetricsEndpoint.class
        ))
        .authorizeRequests((requests) -> requests.anyRequest().hasRole("ENDPOINT_ADMIN"))
        .httpBasic();;
  }
}
