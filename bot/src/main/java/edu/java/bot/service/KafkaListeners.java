package edu.java.bot.service;

import edu.java.bot.dto.request.SendUpdateRequest;
import edu.java.bot.util.SendLinkUpdateToBot;
import io.micrometer.core.instrument.Counter;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
@EnableKafka
@RequiredArgsConstructor
public class KafkaListeners {
    private final SendLinkUpdateToBot sendLinkUpdateToBot;

    private final Counter processedMessagesCounter;

    @KafkaListener(
            topics = "${app.topics.link-update.name}",
            groupId = "${app.topics.link-update.name}",
            containerFactory = "linkUpdatesKafkaListenerContainerFactory",
            autoStartup = "true"
    )
    public void listen(@Payload SendUpdateRequest request) {
        processedMessagesCounter.increment();
        sendLinkUpdateToBot.send(request);
    }
}
