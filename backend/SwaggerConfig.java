package com.ssafy.coco;
import static com.google.common.base.Predicates.or;
import static springfox.documentation.builders.PathSelectors.regex;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.common.base.Predicate;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

	@Bean
	public Docket postsApi() {
		return new Docket(DocumentationType.SWAGGER_2).groupName("public-api")
				.apiInfo(apiInfo()).select().paths(postPaths()).build();
	}

	private Predicate<String> postPaths() {
		return or(regex("/*/*.*"), regex("/*.*"));
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("SSAFY API")
			.description("SSAFY API Reference for Developers")
			.termsOfServiceUrl("https://edu.ssafy.com")
			.contact(
				new Contact("SEESAW", "https://edu.ssafy.com",
				"ssafy@ssafy.com"))
			.license("SSAFY License 0.9")
			.licenseUrl("https://edu.ssafy.com/license.html")
			.version("0.9")
			.build();
	}

}