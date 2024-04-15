package edu.java.client;

import edu.java.exception.ApiException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

public abstract class AbstractClient {
    protected final WebClient webClient;

    public AbstractClient(String baseUrl, Retry retry) {
        this.webClient = WebClient.builder()
                .baseUrl(baseUrl)
                .filter((request, next) -> next
                        .exchange(request)
                        .retryWhen(retry)
                )
                .defaultHeader("Accept", MediaType.APPLICATION_JSON_VALUE)
                .defaultStatusHandler(
                        status -> status.is4xxClientError() || status.is5xxServerError(),
                        this::createApiException
                )
                .build();
    }

    protected Mono<ApiException> createApiException(ClientResponse clientResponse) {
        HttpStatus httpStatus = (HttpStatus) clientResponse.statusCode();

        return clientResponse
                .bodyToMono(String.class)
                .flatMap(errorBody -> Mono.error(new ApiException(
                        httpStatus.getReasonPhrase(),
                        httpStatus.value(),
                        errorBody
                )));
    }
}
