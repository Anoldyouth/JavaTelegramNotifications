package edu.java.scrapper;

import com.github.tomakehurst.wiremock.WireMockServer;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.configureFor;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import edu.java.client.github.GitHubClient;
import edu.java.configuration.properties.GitHubConfig;
import edu.java.util.GithubUpdatesExtractor;
import java.net.URI;
import java.time.OffsetDateTime;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GitHubUpdatesExtractorUnitTest {
    private WireMockServer wireMockServer;
    private GithubUpdatesExtractor githubUpdatesExtractor;

    @BeforeEach
    public void setup() {
        wireMockServer = new WireMockServer();
        wireMockServer.start();
        configureFor("localhost", wireMockServer.port());

        GitHubClient client = new GitHubClient(new GitHubConfig("http://localhost:" + wireMockServer.port(), 50));
        GitHubConfig config = new GitHubConfig("test", 1);
        githubUpdatesExtractor = new GithubUpdatesExtractor(config, client);
    }

    @AfterEach
    public void teardown() {
        wireMockServer.stop();
    }

    @Test
    public void baseRequest() {
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
                  }
                ]
                """;

        String emptyResponse = "[]";

        stubFor(get(urlEqualTo("/repos/owner/repo/events?per_page=1&page=1"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(response)));

        stubFor(get(urlEqualTo("/repos/owner/repo/events?per_page=1&page=2"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(emptyResponse)));

        OffsetDateTime datetime = OffsetDateTime.now().withYear(2000);

        var updates = githubUpdatesExtractor.getUpdates(
                URI.create("https://github.com/owner/repo"),
                datetime
        );

        assertThat(updates.size()).isEqualTo(1);
        assertThat(updates.getFirst().message()).contains("Тип события: PushEvent");
    }
}
