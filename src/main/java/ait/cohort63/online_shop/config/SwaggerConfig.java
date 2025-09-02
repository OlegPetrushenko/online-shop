package ait.cohort63.online_shop.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(
        info = @Info(
                title = "Application Online Shop",
                description = "Application for various operations with Customers and Products",
                version = "1.0.0",
                contact = @Contact(
                        name = "Oleg P.",
                        email = "oleg-p@g.com",
                        url = "https://example"
                )
        )
)

public class SwaggerConfig {
}