package edu.java.bot.configuration.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "links-list", ignoreUnknownFields = false)
public record LinksListConfig(
        long limit
) {
}
