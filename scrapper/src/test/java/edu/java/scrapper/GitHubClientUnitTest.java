package edu.java.scrapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.tomakehurst.wiremock.WireMockServer;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.configureFor;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import edu.java.client.github.GitHubClient;
import edu.java.client.github.dto.request.ListRepositoryEventsRequest;
import edu.java.client.github.dto.response.RepositoryEvent;
import edu.java.configuration.properties.GitHubConfig;
import edu.java.exception.ApiException;
import java.time.Duration;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.util.retry.Retry;

public class GitHubClientUnitTest {
    private WireMockServer wireMockServer;
    private GitHubClient client;

    @BeforeEach
    public void setup() {
        wireMockServer = new WireMockServer();
        wireMockServer.start();
        configureFor("localhost", wireMockServer.port());

        client = new GitHubClient(
                new GitHubConfig("http://localhost:" + wireMockServer.port(), 50),
                Retry.fixedDelay(1, Duration.ofSeconds(1))
        );
    }

    @AfterEach
    public void teardown() {
        wireMockServer.stop();
    }

    @Test
    public void baseRequest() throws JsonProcessingException {
        String response = """
                [
                  {
                    "id": "22249084964",
                    "type": "PushEvent",
                    "actor": {
                      "id": 583231,
                      "login": "octocat",
                      "display_login": "octocat",
                      "gravatar_id": "",
                      "url": "https://api.github.com/users/octocat",
                      "avatar_url": "https://avatars.githubusercontent.com/u/583231?v=4"
                    },
                    "repo": {
                      "id": 1296269,
                      "name": "octocat/Hello-World",
                      "url": "https://api.github.com/repos/octocat/Hello-World"
                    },
                    "payload": {
                      "push_id": 10115855396,
                      "size": 1,
                      "distinct_size": 1,
                      "ref": "refs/heads/master",
                      "head": "7a8f3ac80e2ad2f6842cb86f576d4bfe2c03e300",
                      "before": "883efe034920928c47fe18598c01249d1a9fdabd",
                      "commits": [
                        {
                          "sha": "7a8f3ac80e2ad2f6842cb86f576d4bfe2c03e300",
                          "author": {
                            "email": "octocat@github.com",
                            "name": "Monalisa Octocat"
                          },
                          "message": "commit",
                          "distinct": true,
                          "url": "https://api.github.com/repos/octocat/Hello-World/commits/7a8f3ac80e2ad2f6842cb86f576d4bfe2c03e300"
                        }
                      ]
                    },
                    "public": true,
                    "created_at": "2022-06-09T12:47:28Z"
                  },
                  {
                    "id": "22237752260",
                    "type": "WatchEvent",
                    "actor": {
                      "id": 583231,
                      "login": "octocat",
                      "display_login": "octocat",
                      "gravatar_id": "",
                      "url": "https://api.github.com/users/octocat",
                      "avatar_url": "https://avatars.githubusercontent.com/u/583231?v=4"
                    },
                    "repo": {
                      "id": 1296269,
                      "name": "octocat/Hello-World",
                      "url": "https://api.github.com/repos/octocat/Hello-World"
                    },
                    "payload": {
                      "action": "started"
                    },
                    "public": true,
                    "created_at": "2022-06-08T23:29:25Z"
                  }
                ]
                """;

        stubFor(get(urlEqualTo("/repos/owner/repo/events?per_page=30&page=1"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(response)));

        List<RepositoryEvent> result = client
                .listRepositoryEvents("owner", "repo", new ListRepositoryEventsRequest());

        assertNotNull(result);
        assertEquals(2, result.size());
        assertThat(result.getFirst().id()).isEqualTo("22249084964");
    }

    @Test
    public void customRequest() throws JsonProcessingException {
        var request = new ListRepositoryEventsRequest();
        request.setPage(2);
        request.setPerPage(5);

        String response = """
                [
                  {
                    "id": "22249084964",
                    "type": "PushEvent",
                    "actor": {
                      "id": 583231,
                      "login": "octocat",
                      "display_login": "octocat",
                      "gravatar_id": "",
                      "url": "https://api.github.com/users/octocat",
                      "avatar_url": "https://avatars.githubusercontent.com/u/583231?v=4"
                    },
                    "repo": {
                      "id": 1296269,
                      "name": "octocat/Hello-World",
                      "url": "https://api.github.com/repos/octocat/Hello-World"
                    },
                    "payload": {
                      "push_id": 10115855396,
                      "size": 1,
                      "distinct_size": 1,
                      "ref": "refs/heads/master",
                      "head": "7a8f3ac80e2ad2f6842cb86f576d4bfe2c03e300",
                      "before": "883efe034920928c47fe18598c01249d1a9fdabd",
                      "commits": [
                        {
                          "sha": "7a8f3ac80e2ad2f6842cb86f576d4bfe2c03e300",
                          "author": {
                            "email": "octocat@github.com",
                            "name": "Monalisa Octocat"
                          },
                          "message": "commit",
                          "distinct": true,
                          "url": "https://api.github.com/repos/octocat/Hello-World/commits/7a8f3ac80e2ad2f6842cb86f576d4bfe2c03e300"
                        }
                      ]
                    },
                    "public": true,
                    "created_at": "2022-06-09T12:47:28Z"
                  },
                  {
                    "id": "22237752260",
                    "type": "WatchEvent",
                    "actor": {
                      "id": 583231,
                      "login": "octocat",
                      "display_login": "octocat",
                      "gravatar_id": "",
                      "url": "https://api.github.com/users/octocat",
                      "avatar_url": "https://avatars.githubusercontent.com/u/583231?v=4"
                    },
                    "repo": {
                      "id": 1296269,
                      "name": "octocat/Hello-World",
                      "url": "https://api.github.com/repos/octocat/Hello-World"
                    },
                    "payload": {
                      "action": "started"
                    },
                    "public": true,
                    "created_at": "2022-06-08T23:29:25Z"
                  }
                ]
                """;

        stubFor(get(urlEqualTo("/repos/owner/repo/events?per_page=5&page=2"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(response)));

        List<RepositoryEvent> result = client
                .listRepositoryEvents("owner", "repo", request);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertThat(result.getFirst().id()).isEqualTo("22249084964");
    }

    @Test
    public void notFound() {
        String response = """
                {
                    "message": "Not Found",
                    "documentation_url": "documentation"
                }
                """;

        stubFor(get(urlEqualTo("/repos/owner/repo/events?per_page=30&page=1"))
                .willReturn(aResponse()
                        .withStatus(404)
                        .withHeader("Content-Type", "application/json")
                        .withBody(response)));

        ApiException exception = (ApiException) catchThrowable(
                () -> client.listRepositoryEvents(
                        "owner",
                        "repo",
                        new ListRepositoryEventsRequest()
                )).getCause();
        assertThat(exception.getCode()).isEqualTo(404);
        assertThat(exception.getResponseBody()).isEqualTo(response);
    }
}
