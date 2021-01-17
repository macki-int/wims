package com.mj.wims.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.List;

import static java.util.Collections.singletonList;

@Configuration
@EnableSwagger2
public class Swagger2Config {
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build();


//        regiturn new Docket(DocumentationType.SWAGGER_2)
//                .select()
//                .apis(RequestHandlerSelectors
//                        .basePackage("net.guides.springboot2.springboot2swagger2.controller")
//                .paths(PathSelectors.regex("/.*"))
//                .build().apiInfo(apiEndPointsInfo());
    }
    private ApiInfo apiEndPointsInfo() {
        return new ApiInfoBuilder().title("Spring Boot REST API")
                .description("Employee Management REST API")
                .contact(new Contact("Ramesh Fadatare", "www.javaguides.net", "ramesh24fadatare@gmail.com"))
                .license("Apache 2.0")
                .licenseUrl("http://www.apache.org/licenses/LICENSE-2.0.html")
                .version("1.0.0")
                .build();
    }
//
//    private SecurityContext createContext() {
//        return SecurityContext.builder()
//                .securityReferences(createRef())
//                .forPaths(PathSelectors.any())
//                .build();
//    }

//    private List<SecurityReference> createRef() {
//        AuthorizationScope authorizationScope = new AuthorizationScope(
//                "global", "accessEverything");
//        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
//        authorizationScopes[0] = authorizationScope;
//
//        return singletonList(new SecurityReference("apiKey", authorizationScopes));
//    }
//
//    private SecurityScheme createSchema() {
//
//        return new ApiKey("apiKey", "Authorization", "header");
//    }

}
