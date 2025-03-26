package com.ssafy.s12p21d206.achu.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.formLogin(AbstractHttpConfigurer::disable);
    http.httpBasic(AbstractHttpConfigurer::disable);
    http.csrf(AbstractHttpConfigurer::disable);

    http.authorizeHttpRequests(auth -> auth.requestMatchers("/verification/**")
        .permitAll()
        .requestMatchers(HttpMethod.POST, "/users")
        .permitAll()
        .requestMatchers(HttpMethod.GET, "/users/username/is-unique")
        .permitAll()
        .requestMatchers(HttpMethod.GET, "/users/nickname/is-unique")
        .permitAll()
        .anyRequest()
        .permitAll());
    return http.build();
  }
}
