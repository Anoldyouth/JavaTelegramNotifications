package edu.java.client.bot;

import edu.java.client.AbstractClient;
import edu.java.client.bot.dto.request.SendUpdatesRequest;
import edu.java.configuration.BotConfig;
import reactor.core.publisher.Mono;

public class BotClient extends AbstractClient {
    public BotClient(BotConfig config) {
        super(config.baseUrl());
    }

    public void updates(SendUpdatesRequest request) {
        this.webClient
                .post()
                .uri("/updates")
                .body(Mono.just(request), SendUpdatesRequest.class)
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }
}
