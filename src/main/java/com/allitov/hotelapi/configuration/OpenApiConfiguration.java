package com.allitov.hotelapi.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * The configuration class for OpenAPI.
 * @author allitov
 */
@Configuration
public class OpenApiConfiguration {

    @Value("${server.port}")
    private String serverPort;

    @Bean
    public OpenAPI openApiDescription() {
        Server localhostServer = new Server()
                .url("http://localhost:" + serverPort)
                .description("Local env");

        Info info = new Info()
                .title("Hotel API")
                .version("1.0")
                .description("API for hotel services");

        return new OpenAPI().info(info).servers(List.of(localhostServer));
    }
}
