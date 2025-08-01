package com.api.demo.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @Info(
                title = "Star wars api",
                version = "v1",
                description = "Star wars api"
        ),
        servers = {
                @Server(url = "/", description = "server local")
        }
)
@Configuration
public class SwConfig {
}