package edu.java.client.github;

import edu.java.client.AbstractClient;
import edu.java.client.github.dto.request.ListRepositoryEventsRequest;
import edu.java.client.github.dto.response.RepositoryEvent;
import edu.java.configuration.properties.GitHubConfig;
import java.util.List;
import org.springframework.http.MediaType;
import reactor.util.retry.Retry;

public class GitHubClient extends AbstractClient {
    public GitHubClient(GitHubConfig config, Retry retry) {
        super(config.baseUrl(), retry);
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
