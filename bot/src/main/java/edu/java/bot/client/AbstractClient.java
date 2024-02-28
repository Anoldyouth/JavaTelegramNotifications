package edu.java.bot.client;

import edu.java.bot.exception.ApiException;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public abstract class AbstractClient {
    protected final WebClient webClient;

    public AbstractClient(String baseUrl) {
        this.webClient = WebClient.builder().baseUrl(baseUrl).build();
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
