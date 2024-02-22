package edu.java.client;

import org.springframework.web.reactive.function.client.WebClient;

public abstract class AbstractClient {
    protected final WebClient webClient;

    public AbstractClient(String baseUrl) {
        this.webClient = WebClient.builder().baseUrl(baseUrl).build();
    }
}
