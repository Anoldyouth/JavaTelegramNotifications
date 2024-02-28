package edu.java.configuration;

import edu.java.client.bot.BotClient;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class BotClientConfiguration {
    private final BotConfig botConfig;

    @Bean
    public BotClient botClient() {
        return new BotClient(botConfig);
    }
}
