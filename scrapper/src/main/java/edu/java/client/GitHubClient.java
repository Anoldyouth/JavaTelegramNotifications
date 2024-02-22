package edu.java.client;

import edu.java.client.request.ListRepositoryEventsRequest;
import edu.java.client.response.github.RepositoryEvent;
import edu.java.configuration.GitHubConfig;
import org.springframework.http.MediaType;
import reactor.core.publisher.Flux;

public class GitHubClient extends AbstractClient {
    public GitHubClient(GitHubConfig config) {
        super(config.baseUrl());
    }

    public Flux<RepositoryEvent> listRepositoryEvents(String owner, String repo, ListRepositoryEventsRequest request) {
        return this.webClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path("/repos/{owner}/{repo}/events")
                        .queryParam("per_page", request.getPerPage())
                        .queryParam("page", request.getPage())
                        .build(owner, repo)
                )
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToFlux(RepositoryEvent.class);
    }
}
