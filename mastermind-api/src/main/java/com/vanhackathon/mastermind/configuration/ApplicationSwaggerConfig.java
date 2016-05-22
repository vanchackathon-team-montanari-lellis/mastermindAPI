package com.vanhackathon.mastermind.configuration;

import org.springframework.context.annotation.Bean;

import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * Swagger configuration class.
 * 
 * @author lmontanari (lucas_montanari@hotmail.com)
 */
public class ApplicationSwaggerConfig {

	@Bean
	public Docket docket() {
		Docket docket = new Docket(DocumentationType.SWAGGER_2);

		docket = docket.apiInfo(apiInfo());

		return docket;
	}

	private ApiInfo apiInfo() {
		ApiInfo apInfo = new ApiInfo("Mastermind API",
				"This is the Mastermind backend server. You can find out more about Mastermind at https://en.wikipedia.org/wiki/Mastermind_(board_game).",
				"1.0", "Mastermind API", "http://www.gnu.org/licenses/gpl-3.0.html",
				"Bruno Lellis (brunolellis@gmail.com) Lucas Montanari (lucas_montanari@hotmail.com)",
				"http://www.gnu.org/licenses/gpl-3.0.html");
		return apInfo;
	}
}
