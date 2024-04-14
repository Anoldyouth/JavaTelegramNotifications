package edu.java.configuration;

import edu.java.client.bot.BotClient;
import edu.java.client.bot.dto.request.SendUpdatesRequest;
import edu.java.service.send_link_update.ClientSendLinkUpdate;
import edu.java.service.send_link_update.KafkaSendLinkUpdate;
import edu.java.service.send_link_update.SendLinkUpdate;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;

@Configuration
public class LinkUpdateConfiguration {
    @Bean
    @ConditionalOnProperty(prefix = "app", name = "link-update-method", havingValue = "client")
    public SendLinkUpdate client(BotClient client) {
        return new ClientSendLinkUpdate(client);
    }

    @Bean
    @ConditionalOnProperty(prefix = "app", name = "link-update-method", havingValue = "kafka")
    public SendLinkUpdate kafka(
            KafkaTemplate<String, SendUpdatesRequest> template,
            NewTopic linkUpdateTopic
    ) {
        return new KafkaSendLinkUpdate(template, linkUpdateTopic);
    }
}
