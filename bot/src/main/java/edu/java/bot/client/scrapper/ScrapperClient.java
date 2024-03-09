package edu.java.bot.client.scrapper;

import edu.java.bot.client.AbstractClient;
import edu.java.bot.client.scrapper.dto.request.link.CreateLinkRequest;
import edu.java.bot.client.scrapper.dto.request.link.SearchLinksRequest;
import edu.java.bot.client.scrapper.dto.request.tg_chat_state.ReplaceTgChatStateRequest;
import edu.java.bot.client.scrapper.dto.response.link.LinkResponse;
import edu.java.bot.client.scrapper.dto.response.link.SearchLinksResponse;
import edu.java.bot.client.scrapper.dto.response.tg_chat_state.TgChatStateResponse;
import edu.java.bot.configuration.properties.ScrapperConfig;
import java.net.URI;
import org.springframework.web.util.UriBuilder;
import reactor.core.publisher.Mono;

@SuppressWarnings("MultipleStringLiterals")
public class ScrapperClient extends AbstractClient {
    public ScrapperClient(ScrapperConfig config) {
        super(config.baseUrl());
    }

    // tg-chat
    public void createTgChat(long id) {
        this.webClient
                .post()
                .uri("/tg-chat/{id}", id)
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }

    public void deleteTgChat(long id) {
        this.webClient
                .delete()
                .uri("/tg-chat/{id}", id)
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }

    // links
    public LinkResponse createLink(CreateLinkRequest request) {
        return this.webClient
                .post()
                .uri("/links")
                .body(Mono.just(request), CreateLinkRequest.class)
                .retrieve()
                .bodyToMono(LinkResponse.class)
                .block();
    }

    public LinkResponse deleteLink(long id) {
        return this.webClient
                .delete()
                .uri("/links/{id}", id)
                .retrieve()
                .bodyToMono(LinkResponse.class)
                .block();
    }

    public SearchLinksResponse searchLinks(long tgChatId, SearchLinksRequest request) {
        return this.webClient
                .get()
                .uri(uriBuilder -> prepareSearchLinks(uriBuilder, tgChatId, request))
                .retrieve()
                .bodyToMono(SearchLinksResponse.class)
                .block();
    }

    private URI prepareSearchLinks(UriBuilder builder, long tgChatId, SearchLinksRequest request) {
        builder.path("/links/{tgChatId}");

        if (request.type() != null) {
            builder.queryParam("type", request.type());
        }

        if (request.offset() != null) {
            builder.queryParam("offset", request.offset());
        }

        if (request.cursor() != null) {
            builder.queryParam("cursor", request.cursor());
        }

        if (request.limit() != null) {
            builder.queryParam("limit", request.limit());
        }

        return builder.build(tgChatId);
    }

    // tg-chat-state
    public TgChatStateResponse replaceTgChatState(long id, ReplaceTgChatStateRequest request) {
        return this.webClient
                .put()
                .uri("/tg-chat/{id}/state", id)
                .body(Mono.just(request), ReplaceTgChatStateRequest.class)
                .retrieve()
                .bodyToMono(TgChatStateResponse.class)
                .block();
    }

    public TgChatStateResponse getTgChatState(long id) {
        return this.webClient
                .get()
                .uri("/tg-chat/{id}/state", id)
                .retrieve()
                .bodyToMono(TgChatStateResponse.class)
                .block();
    }
}
