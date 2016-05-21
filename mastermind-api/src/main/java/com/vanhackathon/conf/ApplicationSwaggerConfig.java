package com.vanhackathon.conf;

import org.springframework.context.annotation.Bean;

import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

public class ApplicationSwaggerConfig {

	@Bean
	public Docket docket() {
		Docket docket = new Docket(DocumentationType.SWAGGER_2);
		
		return docket;
	}
}
