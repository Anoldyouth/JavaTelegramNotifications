package edu.java.service.send_link_update;

import edu.java.client.bot.dto.request.SendUpdatesRequest;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.kafka.core.KafkaTemplate;

@RequiredArgsConstructor
public class KafkaSendLinkUpdate implements SendLinkUpdate {
    private static final Logger LOGGER = LogManager.getLogger();

    private final KafkaTemplate<String, SendUpdatesRequest> template;

    private final NewTopic linkUpdateTopic;

    @Override
    public void send(SendUpdatesRequest request) {
        LOGGER.info("topic: {}, request: {}", linkUpdateTopic.name(), request);

        template.send(linkUpdateTopic.name(), request);
    }
}
