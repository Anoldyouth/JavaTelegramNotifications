package edu.java.scrapper;

import com.github.tomakehurst.wiremock.WireMockServer;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.configureFor;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import edu.java.client.stackoverflow.StackOverflowClient;
import edu.java.configuration.properties.StackOverflowConfig;
import edu.java.util.StackOverflowUpdatesExtractor;
import java.net.URI;
import java.time.OffsetDateTime;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class StackOverflowUpdatesExtractorUnitTest {
    private WireMockServer wireMockServer;
    private StackOverflowUpdatesExtractor stackOverflowUpdatesExtractor;

    @BeforeEach
    public void setup() {
        wireMockServer = new WireMockServer();
        wireMockServer.start();
        configureFor("localhost", wireMockServer.port());

        StackOverflowClient client = new StackOverflowClient(new StackOverflowConfig("http://localhost:" + wireMockServer.port()));
        stackOverflowUpdatesExtractor = new StackOverflowUpdatesExtractor(client);
    }

    @AfterEach
    public void teardown() {
        wireMockServer.stop();
    }

    @Test
    public void baseRequest() {
        String response = """
                {
                  "items": [{
                      "link": "https://example.stackexchange.com/questions/1234/an-example-post-title",
                      "title": "An example post title",
                      "comments": [{
                        "owner": {
                            "display_name": "Example User",
                            "link": "https://example.stackexchange.com/users/1/example-user"
                        },
                        "creation_date": 1708722865
                      }],
                      "answers": [{
                        "comments": [{
                            "owner": {
                                "display_name": "Example User",
                                "link": "https://example.stackexchange.com/users/1/example-user"
                            },
                            "creation_date": 1708722865
                        }],
                        "owner": {
                            "display_name": "Example User",
                            "link": "https://example.stackexchange.com/users/1/example-user"
                        },
                        "creation_date": 1708722865
                      }]
                  }]
                }""";

        stubFor(get(urlEqualTo("/questions/1?site=stackoverflow&page=1&pagesize=1&filter=" + StackOverflowUpdatesExtractor.FILTER))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(response)));

        OffsetDateTime datetime = OffsetDateTime.now().withYear(2000);

        var updates = stackOverflowUpdatesExtractor.getUpdates(
                URI.create("https://stackoverflow.com/questions/1/test"),
                datetime
        );

        assertThat(updates.size()).isEqualTo(3);
    }
}
