package com.dev.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig{
	
	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
					.select()
					.apis(RequestHandlerSelectors.basePackage("com.dev.resource"))
					.paths(PathSelectors.any())
					.build()
					.apiInfo(getApiInfo());
	}
	
	private ApiInfo getApiInfo() {
		String description = "Essa é uma simples REST API criada para testar diferentes "
							+"Frameworks para desenvolvimento em conjunto com o Spring Boot.";
		
		return new ApiInfoBuilder()
					.title("Simple REST API")
					.description(description)
					.version("Versão 1.0")
					.license("Apache License Version 2.0")
					.licenseUrl("https://www.apache.org/licenses/LICENSE-2.0\"")
					.contact(new Contact("Ivan Simplício",
										"https://github.com/ivansimplicio",
										"ivansimplicio@outlook.com"))
					.build();
	}
}