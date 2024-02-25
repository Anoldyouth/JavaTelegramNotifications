package edu.java.client;

import edu.java.client.request.ListRepositoryEventsRequest;
import edu.java.client.response.github.RepositoryEvent;
import edu.java.configuration.GitHubConfig;
import java.util.List;
import org.springframework.http.MediaType;

public class GitHubClient extends AbstractClient {
    public GitHubClient(GitHubConfig config) {
        super(config.baseUrl());
    }

    public List<RepositoryEvent> listRepositoryEvents(String owner, String repo, ListRepositoryEventsRequest request) {
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
                .bodyToFlux(RepositoryEvent.class)
                .collectList()
                .block();
    }
}
