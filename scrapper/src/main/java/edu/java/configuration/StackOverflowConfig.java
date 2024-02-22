package edu.java.configuration;

import jakarta.validation.constraints.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "stack-overflow-client", ignoreUnknownFields = false)
public record StackOverflowConfig(
    @NotNull
    String baseUrl
) {
}
