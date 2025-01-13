package com.unitconverter.flexion.app;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfiguration {

    @Bean
    public OpenAPI defineOpenApi() {
        Server server = new Server();
        server.setUrl("http://localhost:8080");

        Info information = new Info()
                .title("Unit of Measure Conversion Validation API")
                .version("1.0")
                .description("This API exposes endpoints to validate unit of measure conversion answers provided by students.");
        return new OpenAPI().info(information).servers(List.of(server));
    }
}
