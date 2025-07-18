package com.linktic.products.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
    info = @Info(title = "Products API", version = "v0.0.0"),
    servers = {@Server(url = "/products/api/", description = "Development server")})
public class SpringDocConfig {}
