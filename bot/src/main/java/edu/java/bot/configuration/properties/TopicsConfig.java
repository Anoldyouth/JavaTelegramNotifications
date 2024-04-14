package edu.java.bot.configuration.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "app.topics", ignoreUnknownFields = false)
public record TopicsConfig(
    Topic linkUpdate
) {
    public record Topic(String name, int partitions, int replicas) {
    }
}
