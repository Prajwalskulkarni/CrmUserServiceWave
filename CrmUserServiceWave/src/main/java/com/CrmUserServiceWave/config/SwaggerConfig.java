package com.CrmUserServiceWave.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;

@Component
public class SwaggerConfig {
	 @Bean
	    public OpenAPI customOpenAPI() {
	        return new OpenAPI()
	            .info(new Info()
	                .title("TaskTracker Project")
	                .version("1.0")
	                .description("API documentation for Coffie Project "))
	            .servers(List.of(
	                    new Server().url("https://internaltomcat.diwise.in/Pandoza_Admin/"),
	                    new Server().url("http://localhost:9004/")));

		      

	    }
}
