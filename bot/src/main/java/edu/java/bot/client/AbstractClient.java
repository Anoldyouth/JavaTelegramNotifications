package edu.java.bot.client;

import edu.java.bot.exception.ApiException;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public abstract class AbstractClient {
    protected final WebClient webClient;

    public AbstractClient(String baseUrl) {
        this.webClient = WebClient.builder().baseUrl(baseUrl).build();
    }

    protected Mono<ApiException> createApiException(ClientResponse clientResponse) {
        ResponseInformation information;

        if (clientResponse.statusCode().is4xxClientError()) {
            information = switch (clientResponse.statusCode().value()) {
                case 401 -> new ResponseInformation(401, "Unauthorized");
                case 403 -> new ResponseInformation(403, "Forbidden");
                case 404 -> new ResponseInformation(404, "Not Found");
                case 405 -> new ResponseInformation(405, "Method Not Allowed");
                case 409 -> new ResponseInformation(409, "Request Timeout");
                default -> new ResponseInformation(400, "Bad Request");
            };
        } else {
            information = switch (clientResponse.statusCode().value()) {
                case 502 -> new ResponseInformation(502, "Bad Gateway");
                case 503 -> new ResponseInformation(503, "Server Unavailable");
                default -> new ResponseInformation(500, "Internal Server Error");
            };
        }

        return clientResponse
                .bodyToMono(String.class)
                .flatMap(errorBody -> Mono.error(new ApiException(
                        information.message,
                        information.status,
                        errorBody
                )));
    }

    private record ResponseInformation(
            int status,
            String message
    ) {
    }
}
