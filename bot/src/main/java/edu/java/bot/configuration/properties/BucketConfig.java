package edu.java.bot.configuration.properties;

import java.time.Duration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "bucket", ignoreUnknownFields = false)
public record BucketConfig(
        int capacity,
        int tokens,
        Duration period
) {
}
