package com.kh.clock.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.kh.clock.intercetor.LoginInterceptor;

@Configuration
public class WebConfig implements WebMvcConfigurer  {
	@Value("${client.origins}")
	private String origins;

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**").allowedOrigins(origins).allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE")
				.allowedHeaders("*").allowCredentials(true);

	}
	
	

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		// registry.addInterceptor(new LoginInterceptor())
		registry.addInterceptor(new LoginInterceptor()).addPathPatterns("/login/logout").excludePathPatterns("/email/*")
		;

	}

	
	
}
