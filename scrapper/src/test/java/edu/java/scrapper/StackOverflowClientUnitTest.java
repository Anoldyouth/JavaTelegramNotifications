package edu.java.scrapper;

import com.github.tomakehurst.wiremock.WireMockServer;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.configureFor;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import edu.java.client.stackoverflow.StackOverflowClient;
import edu.java.client.stackoverflow.dto.request.GetQuestionsByIdsRequest;
import edu.java.client.stackoverflow.dto.response.Question;
import edu.java.configuration.StackOverflowConfig;
import java.time.OffsetDateTime;
import java.util.List;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class StackOverflowClientUnitTest {
    private WireMockServer wireMockServer;
    private StackOverflowClient client;

    @BeforeEach
    public void setup() {
        wireMockServer = new WireMockServer();
        wireMockServer.start();
        configureFor("localhost", wireMockServer.port());

        client = new StackOverflowClient(new StackOverflowConfig("http://localhost:" + wireMockServer.port()));
    }

    @AfterEach
    public void teardown() {
        wireMockServer.stop();
    }

    @Test
    public void baseRequest() {
        String response = """
                {
                  "tags": [
                    "windows",
                    "c#",
                    ".net"
                  ],
                  "owner": {
                    "reputation": 9001,
                    "user_id": 1,
                    "user_type": "registered",
                    "accept_rate": 55,
                    "profile_image": "https://www.gravatar.com/avatar/a007be5a61f6aa8f3e85ae2fc18dd66e?d=identicon&r=PG",
                    "display_name": "Example User",
                    "link": "https://example.stackexchange.com/users/1/example-user"
                  },
                  "is_answered": false,
                  "view_count": 31415,
                  "favorite_count": 1,
                  "down_vote_count": 2,
                  "up_vote_count": 3,
                  "answer_count": 0,
                  "score": 1,
                  "last_activity_date": 1708766065,
                  "creation_date": 1708722865,
                  "last_edit_date": 1708791265,
                  "question_id": 1234,
                  "link": "https://example.stackexchange.com/questions/1234/an-example-post-title",
                  "title": "An example post title",
                  "body": "An example post body"
                }""";

        stubFor(get(urlEqualTo("/questions/1?site=stackoverflow&page=1&pagesize=100"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(response)));

        List<Question> result = client
                .getQuestionsByIds(new int[]{1}, new GetQuestionsByIdsRequest());

        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    public void customRequest() {
        var request = getGetQuestionsByIdsRequest();

        String response = """
                {
                  "tags": [
                    "windows",
                    "c#",
                    ".net"
                  ],
                  "owner": {
                    "reputation": 9001,
                    "user_id": 1,
                    "user_type": "registered",
                    "accept_rate": 55,
                    "profile_image": "https://www.gravatar.com/avatar/a007be5a61f6aa8f3e85ae2fc18dd66e?d=identicon&r=PG",
                    "display_name": "Example User",
                    "link": "https://example.stackexchange.com/users/1/example-user"
                  },
                  "is_answered": false,
                  "view_count": 31415,
                  "favorite_count": 1,
                  "down_vote_count": 2,
                  "up_vote_count": 3,
                  "answer_count": 0,
                  "score": 1,
                  "last_activity_date": 1708766065,
                  "creation_date": 1708722865,
                  "last_edit_date": 1708791265,
                  "question_id": 1234,
                  "link": "https://example.stackexchange.com/questions/1234/an-example-post-title",
                  "title": "An example post title",
                  "body": "An example post body"
                }""";

        stubFor(get(urlEqualTo(
                "/questions/1?site=stackoverflow&page=2&pagesize=5&fromdate=1708732800&todate=1708819200&order=desc" +
                        "&min=1708905600&max=1708992000&sort=activity&filter=!9gzgiGOvI"
                ))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(response)));

        List<Question> result = client
                .getQuestionsByIds(new int[]{1}, request);

        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @NotNull
    private static GetQuestionsByIdsRequest getGetQuestionsByIdsRequest() {
        var request = new GetQuestionsByIdsRequest();
        request.setPage(2);
        request.setPageSize(5);
        request.setFromDate(OffsetDateTime.parse("2024-02-24T10:58:12Z"));
        request.setToDate(OffsetDateTime.parse("2024-02-25T10:58:12Z"));
        request.setOrder("desc");
        request.setMin(OffsetDateTime.parse("2024-02-26T10:58:12Z"));
        request.setMax(OffsetDateTime.parse("2024-02-27T10:58:12Z"));
        request.setSort("activity");
        request.setFilter("!9gzgiGOvI");
        return request;
    }
}
