package edu.java.bot.configuration;

import com.pengrad.telegrambot.TelegramBot;
import edu.java.bot.util.ChainMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BotConfiguration {
    @Autowired
    ApplicationConfig applicationConfig;

    @Bean
    public TelegramBot telegramBot() {
        return new TelegramBot(applicationConfig.telegramToken());
    }
}
