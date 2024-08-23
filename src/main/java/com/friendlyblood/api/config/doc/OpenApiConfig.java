package com.friendlyblood.api.config.doc;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Friendly Blood")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Raiane Oliveira")
                                .email("raiane.mateus.oliveira@gmail.com")));
    }
}
