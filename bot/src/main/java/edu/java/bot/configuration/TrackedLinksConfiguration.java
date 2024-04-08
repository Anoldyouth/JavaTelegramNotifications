package edu.java.bot.configuration;

import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TrackedLinksConfiguration {
    @Bean
    public List<String> patterns() {
        return List.of(
                "^(?:https?://)?(?:www\\.)?github\\.com/([^/]+)/([^/]+)(?:\\.git)?$",
                "^(?:https?://)?(?:www\\.)?stackoverflow\\.com/questions/(\\d+).*"
        );
    }
}
