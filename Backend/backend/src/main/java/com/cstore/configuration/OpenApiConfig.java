package com.cstore.configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
    info = @Info(
            contact = @Contact(
                name = "Perera M. D. B.",
                email = "dulina.21@cse.mrt.ac.lk"
            ),
            description = "OpenAPI Documentation for C Store - A Single Vendor E-commerce Platform developed for the CS3043 Database Systems Project",
            license = @License(
                    name = "GNU General Public License v3.0",
                    url = "https://github.com/Dulina-Perera/C-Store/blob/master/LICENSE"
            ),
            title = "OpenAPI Documentation for C Store",
            version = "1.0.0"
    ),
    servers = {
            @Server(
                    description = "Local Environment",
                    url = "http://localhost:8080"
            )
    }
)
public class OpenApiConfig {
}
