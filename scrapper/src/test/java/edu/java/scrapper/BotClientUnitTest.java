package edu.java.scrapper;

import com.github.tomakehurst.wiremock.WireMockServer;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.configureFor;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import edu.java.client.bot.BotClient;
import edu.java.client.bot.dto.request.SendUpdatesRequest;
import edu.java.configuration.properties.BotConfig;
import edu.java.exception.ApiException;
import java.net.URI;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BotClientUnitTest {
    private WireMockServer wireMockServer;
    private BotClient client;

    private final SendUpdatesRequest request = new SendUpdatesRequest(
            1,
            URI.create("https://api.github.com"),
            "test",
            List.of(1L, 2L, 3L)
    );

    @BeforeEach
    public void setup() {
        wireMockServer = new WireMockServer();
        wireMockServer.start();
        configureFor("localhost", wireMockServer.port());

        client = new BotClient(new BotConfig("http://localhost:" + wireMockServer.port()));
    }

    @AfterEach
    public void teardown() {
        wireMockServer.stop();
    }

    @Test
    public void updatesSuccess() {
        stubFor(post(urlEqualTo("/updates"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                ));

        assertDoesNotThrow(() -> client.updates(request));
    }

    @Test
    public void updatesError() {
        var errorResponse = """
                {
                  "description": "Validation Error",
                  "code": "400",
                  "errors": [
                    {
                      "field": "name",
                      "message": "Поле не должно быть пустым"
                    }
                  ]
                }
                """;

        stubFor(post(urlEqualTo("/updates"))
                .willReturn(aResponse()
                        .withStatus(400)
                        .withHeader("Content-Type", "application/json")
                        .withBody(errorResponse)
                ));

        ApiException exception = (ApiException) catchThrowable(() -> client.updates(request)).getCause();
        assertThat(exception.getCode()).isEqualTo(400);
        assertThat(exception.getResponseBody()).isEqualTo(errorResponse);
    }
}
