package edu.java.client.bot;

import edu.java.client.AbstractClient;
import edu.java.client.bot.dto.request.SendUpdatesRequest;
import edu.java.configuration.properties.BotConfig;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

public class BotClient extends AbstractClient {
    public BotClient(BotConfig config, Retry retry) {
        super(config.baseUrl(), retry);
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
