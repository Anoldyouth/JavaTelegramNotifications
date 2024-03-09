package edu.java.configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @io.swagger.v3.oas.annotations.info.Info(
                title = "Scrapper API",
                version = "1.0.0",
                description = "Scrapper service",
                contact = @Contact(name = "Anoldyouth", url = "https://t.me/Anoldyouth")
        ),
        tags = {
                @Tag(name = "tg-chat", description = "Управление чатами"),
                @Tag(name = "links", description = "Управление ссылками"),
                @Tag(name = "tg-chat-state", description = "Управление состоянием чата пользователя")
        }
)
public class ApiConfiguration {
}
