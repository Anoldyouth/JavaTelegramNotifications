package edu.java.configuration;

import edu.java.client.bot.BotClient;
import edu.java.configuration.properties.BotConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.util.retry.Retry;

@Configuration
@RequiredArgsConstructor
public class BotClientConfiguration {
    private final BotConfig botConfig;
    private final Retry retry;

    @Bean
    public BotClient botClient() {
        return new BotClient(botConfig, retry);
    }
}
