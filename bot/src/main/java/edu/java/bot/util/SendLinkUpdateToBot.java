package edu.java.bot.util;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.dto.request.SendUpdateRequest;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SendLinkUpdateToBot {
    private static final Logger LOGGER = LogManager.getLogger();

    private final TelegramBot bot;

    private final KafkaTemplate<String, SendUpdateRequest> template;

    private final NewTopic linkUpdateDeadLetterTopic;

    public void send(SendUpdateRequest request) {
        LOGGER.debug("Received request: " + request);
        String message = "Получено новое событие для ссылки " + request.url() + "\n\n" + request.description();
        List<Long> undelivered = new ArrayList<>();

        for (long chatId: request.tgChatIds()) {
            try {
                var botRequest = new SendMessage(chatId, message)
                        .disableWebPagePreview(true)
                        .parseMode(ParseMode.Markdown);

                bot.execute(botRequest);
            } catch (Exception exception) {
                LOGGER.error(exception);

                undelivered.add(chatId);
            }
        }

        if (!undelivered.isEmpty()) {
            template.send(
                    linkUpdateDeadLetterTopic.name(),
                    new SendUpdateRequest(request.url(), request.description(), undelivered)
            );
        }
    }
}
