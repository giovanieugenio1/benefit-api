package com.corporate.benefits.benefit_api.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI BenefitApiOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Beenfit API")
                        .description("API for managing employee benefits")
                        .version("v1.0")
                        .contact(new Contact()
                                .name("Giovani Eugenio")
                                .email("giovani.eugenio.sp@gmail.com"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://www.apache.org/licenses/LICENSE-2.0"))
                )
                        .externalDocs(new ExternalDocumentation()
                                .description("GitHub Repository")
                                .url("https://github.com/giovanieugenio1/benefit-api"));
    }
}
