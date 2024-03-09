package edu.java.bot.configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @io.swagger.v3.oas.annotations.info.Info(
                title = "Bot API",
                version = "1.0.0",
                description = "Bot service",
                contact = @Contact(name = "Anoldyouth", url = "https://t.me/Anoldyouth")
        ),
        tags = {
                @Tag(name = "updates", description = "Отправка обновлений")
        }
)
public class ApiConfiguration {
}
