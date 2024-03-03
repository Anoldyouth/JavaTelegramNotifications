package edu.java.client.stackoverflow;

import edu.java.client.AbstractClient;
import edu.java.client.stackoverflow.dto.request.GetQuestionsByIdsRequest;
import edu.java.client.stackoverflow.dto.response.Question;
import edu.java.configuration.StackOverflowConfig;
import java.net.URI;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.StringJoiner;
import org.springframework.http.MediaType;
import org.springframework.web.util.UriBuilder;

public class StackOverflowClient extends AbstractClient {
    public StackOverflowClient(StackOverflowConfig config) {
        super(config.baseUrl());
    }

    public List<Question> getQuestionsByIds(int[] ids, GetQuestionsByIdsRequest request) {
        return this.webClient
                .get()
                .uri(uriBuilder -> prepareGetQuestionsByIds(uriBuilder, ids, request))
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToFlux(Question.class)
                .collectList()
                .block();
    }

    private URI prepareGetQuestionsByIds(UriBuilder builder, int[] ids, GetQuestionsByIdsRequest request) {
        builder.path("/questions/{ids}")
                .queryParam("site", "stackoverflow")
                .queryParam("page", request.getPage())
                .queryParam("pagesize", request.getPageSize());

        if (request.getFromDate() != null) {
            builder.queryParam(
                    "fromdate",
                    request.getFromDate().toLocalDate().toEpochSecond(LocalTime.MIN, ZoneOffset.UTC)
            );
        }

        if (request.getToDate() != null) {
            builder.queryParam(
                    "todate",
                    request.getToDate().toLocalDate().toEpochSecond(LocalTime.MIN, ZoneOffset.UTC)
            );
        }

        if (request.getOrder() != null) {
            builder.queryParam("order", request.getOrder());
        }

        if (request.getMin() != null) {
            builder.queryParam("min", request.getMin().toLocalDate().toEpochSecond(LocalTime.MIN, ZoneOffset.UTC));
        }

        if (request.getMax() != null) {
            builder.queryParam("max", request.getMax().toLocalDate().toEpochSecond(LocalTime.MIN, ZoneOffset.UTC));
        }

        if (request.getSort() != null) {
            builder.queryParam("sort", request.getSort());
        }

        if (request.getFilter() != null) {
            builder.queryParam("filter", request.getFilter());
        }

        StringJoiner joiner = new StringJoiner(";");
        for (int num : ids) {
            joiner.add(String.valueOf(num));
        }
        return builder.build(joiner.toString());
    }
}
