package edu.java.configuration.properties;

import java.time.Duration;
import java.util.Set;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "app.retry", ignoreUnknownFields = false)
public record RetryConfig(
        Policy policy,
        Duration delay,
        int maxAttempts,
        Duration interval,
        Set<Integer> codes
) {
    public enum Policy {
        FIXED,
        LINEAR,
        EXPONENTIAL
    }
}
