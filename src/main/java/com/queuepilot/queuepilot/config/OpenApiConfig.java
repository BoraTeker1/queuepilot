package com.queuepilot.queuepilot.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * OpenAPI / Swagger configuration.
 */
@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI queuePilotOpenApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("QueuePilot API")
                        .description("SLA-aware incident triage and auto-remediation platform")
                        .version("v1"));
    }
}
