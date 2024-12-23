package br.com.alan.walletServiceAssignment.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition
public class SwaggerConfig {

    @Bean
    public OpenAPI customizeOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Wallet Service Assignment")
                        .description("A wallet service that manages users' money. This service supports deposit, withdrawal, and transfer of funds between users.")
                        .version("0.0.1"));

    }
}

