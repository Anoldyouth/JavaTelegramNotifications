package edu.java.bot.configuration;

import edu.java.bot.client.scrapper.ScrapperClient;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class ScrapperClientConfiguration {
    private final ScrapperConfig scrapperConfig;

    @Bean
    public ScrapperClient botClient() {
        return new ScrapperClient(scrapperConfig);
    }
}
