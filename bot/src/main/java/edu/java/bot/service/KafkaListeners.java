package edu.java.bot.service;

import edu.java.bot.dto.request.SendUpdateRequest;
import edu.java.bot.util.SendLinkUpdateToBot;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;

@EnableKafka
@RequiredArgsConstructor
public class KafkaListeners {
    private final SendLinkUpdateToBot sendLinkUpdateToBot;

    @KafkaListener(
            topics = "${app.topics.link-update.name}",
            containerFactory = "linkUpdatesKafkaListenerContainerFactory",
            autoStartup = "true"
    )
    public void listen(@Payload SendUpdateRequest request) {
        sendLinkUpdateToBot.send(request);
    }
}
