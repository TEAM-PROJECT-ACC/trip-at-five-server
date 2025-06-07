package com.kh.clock.config;

import org.springdoc.core.customizers.GlobalOpenApiCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class SwaggerConfig {

	@Bean
	public OpenAPI cusomOpenApi() {
		return new OpenAPI().info(new Info().title("trip-at-five").version("1.0").description("백엔드 api"));
	}
	
	public ApiResponse createApiResponse(String message, Content content){
		return new ApiResponse().description(message).content(content);
	}
	
	@Bean
	public GlobalOpenApiCustomizer customerGlobalHeaderOpenApiCustomiser() {
		return openApi -> {
        	// 공통으로 사용되는 response 설정 
			openApi.getPaths().values().forEach(pathItem -> pathItem.readOperations().forEach(operation -> {
				ApiResponses apiResponses = operation.getResponses();
				apiResponses.addApiResponse("200", createApiResponse(apiResponses.get("200").getDescription(), apiResponses.get("200").getContent()));
				apiResponses.addApiResponse("400", createApiResponse("Bad Request", null));
				apiResponses.addApiResponse("401", createApiResponse("Access Token Error", null));
				apiResponses.addApiResponse("500", createApiResponse("Server Error", null));
			}));
		};
	}
}
