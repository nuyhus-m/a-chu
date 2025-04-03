package com.ssafy.s12p21d206.achu.chat.controller.config;

import com.ssafy.s12p21d206.achu.chat.controller.support.ChatApiUserArgumentResolver;
import java.util.List;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ChatWebConfig implements WebMvcConfigurer {

  @Override
  public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
    resolvers.add(new ChatApiUserArgumentResolver());
  }
}
