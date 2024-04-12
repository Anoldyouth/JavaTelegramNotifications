package edu.java.bot.configuration;

import edu.java.bot.client.scrapper.ScrapperClient;
import edu.java.bot.configuration.properties.ScrapperConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.util.retry.Retry;

@Configuration
@RequiredArgsConstructor
public class ScrapperClientConfiguration {
    private final ScrapperConfig scrapperConfig;
    private final Retry retry;

    @Bean
    public ScrapperClient scrapperClient() {
        return new ScrapperClient(scrapperConfig, retry);
    }
}
