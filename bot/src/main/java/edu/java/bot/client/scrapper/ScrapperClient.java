package edu.java.bot.client.scrapper;

import edu.java.bot.client.AbstractClient;
import edu.java.bot.client.scrapper.dto.request.link.CreateLinkRequest;
import edu.java.bot.client.scrapper.dto.request.link.SearchLinksRequest;
import edu.java.bot.client.scrapper.dto.request.tg_chat_state.ReplaceTgChatStateRequest;
import edu.java.bot.client.scrapper.dto.response.link.LinkResponse;
import edu.java.bot.client.scrapper.dto.response.link.SearchLinksResponse;
import edu.java.bot.client.scrapper.dto.response.tg_chat_state.TgChatStateResponse;
import edu.java.bot.configuration.ScrapperConfig;
import org.springframework.http.MediaType;
import reactor.core.publisher.Mono;

public class ScrapperClient extends AbstractClient {
    public ScrapperClient(ScrapperConfig config) {
        super(config.baseUrl());
    }

    // tg-chat
    public void createTgChat(long id) {
        this.webClient
                .post()
                .uri("/tg-chat/{id}", id)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(
                        status -> status.is4xxClientError() || status.is5xxServerError(),
                        this::createApiException
                )
                .bodyToMono(Void.class)
                .block();
    }

    public void createTgChatAsync(long id) {
        this.webClient
                .post()
                .uri("/tg-chat/{id}", id)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(
                        status -> status.is4xxClientError() || status.is5xxServerError(),
                        this::createApiException
                )
                .bodyToMono(Void.class)
                .block();
    }

    public void deleteTgChat(long id) {
        this.webClient
                .delete()
                .uri("/tg-chat/{id}", id)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(
                        status -> status.is4xxClientError() || status.is5xxServerError(),
                        this::createApiException
                )
                .bodyToMono(Void.class)
                .block();
    }

    public void deleteTgChatAsync(long id) {
        this.webClient
                .delete()
                .uri("/tg-chat/{id}", id)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(
                        status -> status.is4xxClientError() || status.is5xxServerError(),
                        this::createApiException
                )
                .bodyToMono(Void.class)
                .block();
    }

    // links
    public LinkResponse createLink(CreateLinkRequest request) {
        return this.webClient
                .post()
                .uri("/links")
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(request), CreateLinkRequest.class)
                .retrieve()
                .onStatus(
                        status -> status.is4xxClientError() || status.is5xxServerError(),
                        this::createApiException
                )
                .bodyToMono(LinkResponse.class)
                .block();
    }

    public LinkResponse deleteLink(long id) {
        return this.webClient
                .delete()
                .uri("/links/{id}", id)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(
                        status -> status.is4xxClientError() || status.is5xxServerError(),
                        this::createApiException
                )
                .bodyToMono(LinkResponse.class)
                .block();
    }

    public SearchLinksResponse searchLinks(SearchLinksRequest request) {
        return this.webClient
                .post()
                .uri("/links:search")
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(request), CreateLinkRequest.class)
                .retrieve()
                .onStatus(
                        status -> status.is4xxClientError() || status.is5xxServerError(),
                        this::createApiException
                )
                .bodyToMono(SearchLinksResponse.class)
                .block();
    }

    // tg-chat-state
    public TgChatStateResponse replaceTgChatState(long id, ReplaceTgChatStateRequest request) {
        return this.webClient
                .put()
                .uri("/tg-chat/state/{id}", id)
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(request), ReplaceTgChatStateRequest.class)
                .retrieve()
                .onStatus(
                        status -> status.is4xxClientError() || status.is5xxServerError(),
                        this::createApiException
                )
                .bodyToMono(TgChatStateResponse.class)
                .block();
    }

    public TgChatStateResponse deleteTgChatState(long id) {
        return this.webClient
                .delete()
                .uri("/tg-chat/state/{id}", id)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(
                        status -> status.is4xxClientError() || status.is5xxServerError(),
                        this::createApiException
                )
                .bodyToMono(TgChatStateResponse.class)
                .block();
    }
}
