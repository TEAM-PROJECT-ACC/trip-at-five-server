package com.kh.clock.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
  @Value("${client.origins}")
  private String origins;
  
  @Override
  public void addCorsMappings(CorsRegistry registry) {
    System.out.println(origins);
    registry.addMapping("/**")
      .allowedOrigins(origins)
      .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE")
      .allowedHeaders("*")
      .allowCredentials(true);
  }
}
