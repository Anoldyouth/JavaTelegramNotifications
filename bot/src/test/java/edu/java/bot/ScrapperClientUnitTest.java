package edu.java.bot;

import com.github.tomakehurst.wiremock.WireMockServer;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.configureFor;
import static com.github.tomakehurst.wiremock.client.WireMock.delete;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.put;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import edu.java.bot.client.scrapper.ScrapperClient;
import edu.java.bot.client.scrapper.dto.request.link.CreateLinkRequest;
import edu.java.bot.client.scrapper.dto.request.link.SearchLinksRequest;
import edu.java.bot.client.scrapper.dto.request.tg_chat_state.ReplaceTgChatStateRequest;
import edu.java.bot.configuration.properties.ScrapperConfig;
import edu.java.bot.exception.ApiException;
import edu.java.bot.util.ScenarioDispatcher;
import java.net.URI;
import java.time.Duration;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.util.retry.Retry;

public class ScrapperClientUnitTest {
    private WireMockServer wireMockServer;
    private ScrapperClient client;

    @BeforeEach
    public void setup() {
        wireMockServer = new WireMockServer();
        wireMockServer.start();
        configureFor("localhost", wireMockServer.port());

        client = new ScrapperClient(
                new ScrapperConfig("http://localhost:" + wireMockServer.port()),
                Retry.fixedDelay(1, Duration.ofSeconds(1))
        );
    }

    @AfterEach
    public void teardown() {
        wireMockServer.stop();
    }

    @Test
    public void createTgChatSuccess() {
        stubFor(post(urlEqualTo("/tg-chat/1"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                ));

        assertDoesNotThrow(() -> client.createTgChat(1L));
    }

    @Test
    public void createTgChatError() {
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

        stubFor(post(urlEqualTo("/tg-chat/1"))
                .willReturn(aResponse()
                        .withStatus(400)
                        .withHeader("Content-Type", "application/json")
                        .withBody(errorResponse)
                ));

        ApiException exception = (ApiException) catchThrowable(() -> client.createTgChat(1L)).getCause();
        assertThat(exception.getCode()).isEqualTo(400);
        assertThat(exception.getResponseBody()).isEqualTo(errorResponse);
    }

    @Test
    public void deleteTgChatSuccess() {
        stubFor(delete(urlEqualTo("/tg-chat/1"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                ));

        assertDoesNotThrow(() -> client.deleteTgChat(1L));
    }

    @Test
    public void deleteTgChatError() {
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

        stubFor(delete(urlEqualTo("/tg-chat/1"))
                .willReturn(aResponse()
                        .withStatus(400)
                        .withHeader("Content-Type", "application/json")
                        .withBody(errorResponse)
                ));

        ApiException exception = (ApiException) catchThrowable(() -> client.deleteTgChat(1L)).getCause();
        assertThat(exception.getCode()).isEqualTo(400);
        assertThat(exception.getResponseBody()).isEqualTo(errorResponse);
    }

    @Test
    public void createLinkSuccess() {
        var request = new CreateLinkRequest(URI.create("https://api.github.com"));
        var response = """
                {
                  "id": 1,
                  "url": "https://api.github.com"
                }
                """;

        stubFor(post(urlEqualTo("/links/1"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(response)
                ));

        var clientResponse = client.createLink(1L, request);
        assertThat(clientResponse.id()).isEqualTo(1L);
        assertThat(clientResponse.url().toString()).isEqualTo("https://api.github.com");
    }

    @Test
    public void createLinkError() {
        var request = new CreateLinkRequest(URI.create("https://api.github.com"));
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

        stubFor(post(urlEqualTo("/links/1"))
                .willReturn(aResponse()
                        .withStatus(400)
                        .withHeader("Content-Type", "application/json")
                        .withBody(errorResponse)
                ));

        ApiException exception = (ApiException) catchThrowable(() -> client.createLink(1L, request)).getCause();
        assertThat(exception.getCode()).isEqualTo(400);
        assertThat(exception.getResponseBody()).isEqualTo(errorResponse);
    }

    @Test
    public void searchLinksSuccess() {
        var request = new SearchLinksRequest(1L, 20L);
        var response = """
                {
                  "links": [
                    {
                      "id": 1,
                      "url": "https://api.github.com"
                    }
                  ],
                  "pagination": {
                    "type": "OFFSET",
                    "offset": 20,
                    "total": 20,
                    "limit": 20
                  }
                }
                """;

        stubFor(get(urlEqualTo("/links/1?offset=1&limit=20"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(response)
                ));

        var clientResponse = client.searchLinks(1, request);
        assertThat(clientResponse.links().size()).isEqualTo(1);
    }

    @Test
    public void searchLinksError() {
        var request = new SearchLinksRequest(1L, 20L);
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

        stubFor(get(urlEqualTo("/links/1?offset=1&limit=20"))
                .willReturn(aResponse()
                        .withStatus(400)
                        .withHeader("Content-Type", "application/json")
                        .withBody(errorResponse)
                ));

        ApiException exception = (ApiException) catchThrowable(() -> client.searchLinks(1, request)).getCause();
        assertThat(exception.getCode()).isEqualTo(400);
        assertThat(exception.getResponseBody()).isEqualTo(errorResponse);
    }

    @Test
    public void deleteLinkSuccess() {
        var response = """
                {
                  "id": 1,
                  "url": "https://api.github.com"
                }
                """;

        stubFor(delete(urlEqualTo("/links/2/1"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(response)
                ));

        var clientResponse = client.deleteLink(2, 1);
        assertThat(clientResponse.id()).isEqualTo(1L);
        assertThat(clientResponse.url().toString()).isEqualTo("https://api.github.com");
    }

    @Test
    public void deleteLinkError() {
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

        stubFor(delete(urlEqualTo("/links/2/1"))
                .willReturn(aResponse()
                        .withStatus(400)
                        .withHeader("Content-Type", "application/json")
                        .withBody(errorResponse)
                ));

        ApiException exception = (ApiException) catchThrowable(() -> client.deleteLink(2, 1)).getCause();
        assertThat(exception.getCode()).isEqualTo(400);
        assertThat(exception.getResponseBody()).isEqualTo(errorResponse);
    }

    @Test
    public void getTgChatStateSuccess() {
        var response = """
                {
                  "tgChatId": 1,
                  "state": "MAIN"
                }
                """;

        stubFor(get(urlEqualTo("/tg-chat/1/state"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(response)
                ));

        var clientResponse = client.getTgChatState(1);
        assertThat(clientResponse.tgChatId()).isEqualTo(1L);
        assertThat(clientResponse.state()).isEqualTo(ScenarioDispatcher.ScenarioType.MAIN);
    }

    @Test
    public void getTgChatStateError() {
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

        stubFor(get(urlEqualTo("/tg-chat/1/state"))
                .willReturn(aResponse()
                        .withStatus(400)
                        .withHeader("Content-Type", "application/json")
                        .withBody(errorResponse)
                ));

        ApiException exception = (ApiException) catchThrowable(() -> client.getTgChatState(1L)).getCause();
        assertThat(exception.getCode()).isEqualTo(400);
        assertThat(exception.getResponseBody()).isEqualTo(errorResponse);
    }

    @Test
    public void replaceTgChatStateSuccess() {
        var request = new ReplaceTgChatStateRequest(ScenarioDispatcher.ScenarioType.MAIN);
        var response = """
                {
                  "tgChatId": 1,
                  "state": "MAIN"
                }
                """;

        stubFor(put(urlEqualTo("/tg-chat/1/state"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(response)
                ));

        var clientResponse = client.replaceTgChatState(1, request);
        assertThat(clientResponse.tgChatId()).isEqualTo(1L);
        assertThat(clientResponse.state()).isEqualTo(ScenarioDispatcher.ScenarioType.MAIN);
    }

    @Test
    public void replaceTgChatStateError() {
        var request = new ReplaceTgChatStateRequest(ScenarioDispatcher.ScenarioType.MAIN);
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

        stubFor(put(urlEqualTo("/tg-chat/1/state"))
                .willReturn(aResponse()
                        .withStatus(400)
                        .withHeader("Content-Type", "application/json")
                        .withBody(errorResponse)
                ));

        ApiException exception = (ApiException) catchThrowable(
                () -> client.replaceTgChatState(1, request)
        ).getCause();
        assertThat(exception.getCode()).isEqualTo(400);
        assertThat(exception.getResponseBody()).isEqualTo(errorResponse);
    }
}
