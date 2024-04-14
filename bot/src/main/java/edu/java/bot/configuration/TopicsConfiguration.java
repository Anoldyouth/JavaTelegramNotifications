package edu.java.bot.configuration;

import edu.java.bot.configuration.properties.TopicsConfig;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
@RequiredArgsConstructor
public class TopicsConfiguration {
    private final TopicsConfig topicsConfig;

    @Bean
    public NewTopic linkUpdateTopic() {
        return make(topicsConfig.linkUpdate());
    }

    @Bean
    public NewTopic linkUpdateDeadLetterTopic() {
        return makeDeadLetterTopic(topicsConfig.linkUpdate());
    }

    private NewTopic make(TopicsConfig.Topic topic) {
        return TopicBuilder.name(topic.name())
                .partitions(topic.partitions())
                .replicas(topic.replicas())
                .build();
    }

    private NewTopic makeDeadLetterTopic(TopicsConfig.Topic topic) {
        return TopicBuilder.name(topic.name() + "-dlq")
                .partitions(topic.partitions())
                .replicas(topic.replicas())
                .build();
    }
}
