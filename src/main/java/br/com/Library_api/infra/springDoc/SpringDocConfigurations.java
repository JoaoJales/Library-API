package br.com.Library_api.infra.springDoc;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.parameters.Parameter;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springdoc.core.customizers.OpenApiCustomizer;

@Configuration
public class SpringDocConfigurations {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .components(new Components()
                        .addSecuritySchemes("bearer-key",
                                new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT")))
                .info(new Info()
                        .title("Bank API")
                        .description("API RESTful desenvolvida com **Java + Spring Boot**, que simula a gestão e funcionamento de uma biblioteca, como: consultas de acervo, empréstimos e reservas")
                        .contact(new Contact()
                                .name("João Jales")
                                .email("joaoricardocirino@gmail.com"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("http://library/api/licenca")));
    }

}
