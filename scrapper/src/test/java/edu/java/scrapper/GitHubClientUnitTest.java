package edu.java.scrapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.github.tomakehurst.wiremock.WireMockServer;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.configureFor;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import edu.java.client.GitHubClient;
import edu.java.client.request.ListRepositoryEventsRequest;
import edu.java.client.response.github.RepositoryEvent;
import edu.java.configuration.GitHubConfig;
import java.util.List;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.client.WebClientResponseException;

public class GitHubClientUnitTest {
    private static final EasyRandom GENERATOR = new EasyRandom();
    private static ObjectMapper objectMapper;
    private WireMockServer wireMockServer;
    private GitHubClient client;

    @BeforeAll
    public static void configure() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    @BeforeEach
    public void setup() {
        wireMockServer = new WireMockServer();
        wireMockServer.start();
        configureFor("localhost", wireMockServer.port());

        client = new GitHubClient(new GitHubConfig("http://localhost:" + wireMockServer.port()));
    }

    @AfterEach
    public void teardown() {
        wireMockServer.stop();
    }

    @Test
    public void baseRequest() throws JsonProcessingException {
        String response = objectMapper.writeValueAsString(
                GENERATOR.objects(RepositoryEvent.class, 2).toArray()
        );

        stubFor(get(urlEqualTo("/repos/owner/repo/events?per_page=30&page=1"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(response)));

        List<RepositoryEvent> result = client
                .listRepositoryEvents("owner", "repo", new ListRepositoryEventsRequest());

        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    public void customRequest() throws JsonProcessingException {
        var request = new ListRepositoryEventsRequest();
        request.setPage(2);
        request.setPerPage(5);

        String response = objectMapper.writeValueAsString(
                GENERATOR.objects(RepositoryEvent.class, 2).toArray()
        );

        stubFor(get(urlEqualTo("/repos/owner/repo/events?per_page=5&page=2"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(response)));

        List<RepositoryEvent> result = client
                .listRepositoryEvents("owner", "repo", request);

        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    public void notFound() {
        String response = "{\n" +
                "    \"message\": \"Not Found\",\n" +
                "    \"documentation_url\": \"documentation\"\n" +
                "}";

        stubFor(get(urlEqualTo("/repos/owner/repo/events?per_page=5&page=2"))
                .willReturn(aResponse()
                        .withStatus(404)
                        .withHeader("Content-Type", "application/json")
                        .withBody(response)));

        assertThrows(
                WebClientResponseException.class,
                () -> client.listRepositoryEvents("owner", "repo", new ListRepositoryEventsRequest())
        );
    }
}
