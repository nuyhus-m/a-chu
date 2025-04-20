package com.ssafy.s12p21d206.achu.client.sms.config;

import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.service.DefaultMessageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MessageServiceConfig {

  @Value("${achu.sms.api-key}")
  private String apiKey;

  @Value("${achu.sms.api-secret-key}")
  private String apiSecretKey;

  @Value("${achu.sms.domain}")
  private String domain;

  @Bean
  public DefaultMessageService messageSender() {
    return NurigoApp.INSTANCE.initialize(apiKey, apiSecretKey, domain);
  }
}
