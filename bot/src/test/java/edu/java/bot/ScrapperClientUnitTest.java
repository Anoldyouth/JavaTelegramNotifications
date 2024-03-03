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
import edu.java.bot.client.scrapper.dto.request.link.Filter;
import edu.java.bot.client.scrapper.dto.request.link.PaginationRequest;
import edu.java.bot.client.scrapper.dto.request.link.SearchLinksRequest;
import edu.java.bot.client.scrapper.dto.request.tg_chat_state.ReplaceTgChatStateRequest;
import edu.java.bot.client.scrapper.dto.response.link.CursorPagination;
import edu.java.bot.client.scrapper.dto.response.link.OffsetPagination;
import edu.java.bot.configuration.properties.ScrapperConfig;
import edu.java.bot.exception.ApiException;
import java.net.URI;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ScrapperClientUnitTest {
    private WireMockServer wireMockServer;
    private ScrapperClient client;

    @BeforeEach
    public void setup() {
        wireMockServer = new WireMockServer();
        wireMockServer.start();
        configureFor("localhost", wireMockServer.port());

        client = new ScrapperClient(new ScrapperConfig("http://localhost:" + wireMockServer.port()));
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
        var request = new CreateLinkRequest(1L, URI.create("https://api.github.com"));
        var response = """
                {
                  "id": 1,
                  "url": "https://api.github.com"
                }
                """;

        stubFor(post(urlEqualTo("/links"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(response)
                ));

        var clientResponse = client.createLink(request);
        assertThat(clientResponse.id()).isEqualTo(1L);
        assertThat(clientResponse.url().toString()).isEqualTo("https://api.github.com");
    }

    @Test
    public void createLinkError() {
        var request = new CreateLinkRequest(1L, URI.create("https://api.github.com"));
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

        stubFor(post(urlEqualTo("/links"))
                .willReturn(aResponse()
                        .withStatus(400)
                        .withHeader("Content-Type", "application/json")
                        .withBody(errorResponse)
                ));

        ApiException exception = (ApiException) catchThrowable(() -> client.createLink(request)).getCause();
        assertThat(exception.getCode()).isEqualTo(400);
        assertThat(exception.getResponseBody()).isEqualTo(errorResponse);
    }

    @Test
    public void searchLinksOffsetSuccess() {
        var request = new SearchLinksRequest(
                new Filter(1L),
                new PaginationRequest("OFFSET", 1L, null, 20)
        );
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

        stubFor(post(urlEqualTo("/links:search"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(response)
                ));

        var clientResponse = client.searchLinks(request);
        assertThat(clientResponse.links().length).isEqualTo(1);
        assertThat(clientResponse.pagination()).isInstanceOf(OffsetPagination.class);
        assertThat(clientResponse.pagination()).isNotInstanceOf(CursorPagination.class);
    }

    @Test
    public void searchLinksCursorSuccess() {
        var request = new SearchLinksRequest(
                new Filter(1L),
                new PaginationRequest("CURSOR", null, "test", 20)
        );
        var response = """
                {
                  "links": [
                    {
                      "id": 1,
                      "url": "https://api.github.com"
                    }
                  ],
                  "pagination": {
                      "type": "CURSOR",
                      "cursor": "cursor",
                      "previousCursor": "previousCursor",
                      "nextCursor": "nextCursor",
                      "limit": 20
                  }
                }
                """;

        stubFor(post(urlEqualTo("/links:search"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(response)
                ));

        var clientResponse = client.searchLinks(request);
        assertThat(clientResponse.links().length).isEqualTo(1);
        assertThat(clientResponse.pagination()).isInstanceOf(CursorPagination.class);
        assertThat(clientResponse.pagination()).isNotInstanceOf(OffsetPagination.class);
    }

    @Test
    public void searchLinksError() {
        var request = new SearchLinksRequest(
                new Filter(1L),
                new PaginationRequest("OFFSET", 1L, null, 20)
        );
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

        stubFor(post(urlEqualTo("/links:search"))
                .willReturn(aResponse()
                        .withStatus(400)
                        .withHeader("Content-Type", "application/json")
                        .withBody(errorResponse)
                ));

        ApiException exception = (ApiException) catchThrowable(() -> client.searchLinks(request)).getCause();
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

        stubFor(delete(urlEqualTo("/links/1"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(response)
                ));

        var clientResponse = client.deleteLink(1);
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

        stubFor(delete(urlEqualTo("/links/1"))
                .willReturn(aResponse()
                        .withStatus(400)
                        .withHeader("Content-Type", "application/json")
                        .withBody(errorResponse)
                ));

        ApiException exception = (ApiException) catchThrowable(() -> client.deleteLink(1L)).getCause();
        assertThat(exception.getCode()).isEqualTo(400);
        assertThat(exception.getResponseBody()).isEqualTo(errorResponse);
    }

    @Test
    public void getTgChatStateSuccess() {
        var response = """
                {
                  "tgChatId": 1,
                  "state": 1
                }
                """;

        stubFor(get(urlEqualTo("/tg-chat/state/1"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(response)
                ));

        var clientResponse = client.getTgChatState(1);
        assertThat(clientResponse.tgChatId()).isEqualTo(1L);
        assertThat(clientResponse.state()).isEqualTo((short) 1);
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

        stubFor(get(urlEqualTo("/tg-chat/state/1"))
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
        var request = new ReplaceTgChatStateRequest((short) 1);
        var response = """
                {
                  "tgChatId": 1,
                  "state": 1
                }
                """;

        stubFor(put(urlEqualTo("/tg-chat/state/1"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(response)
                ));

        var clientResponse = client.replaceTgChatState(1, request);
        assertThat(clientResponse.tgChatId()).isEqualTo(1L);
        assertThat(clientResponse.state()).isEqualTo((short) 1);
    }

    @Test
    public void replaceTgChatStateError() {
        var request = new ReplaceTgChatStateRequest((short) 1);
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

        stubFor(put(urlEqualTo("/tg-chat/state/1"))
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
