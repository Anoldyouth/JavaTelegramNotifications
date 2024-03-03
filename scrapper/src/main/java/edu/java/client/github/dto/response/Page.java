package edu.java.client.github.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Generated;

@Generated
public record Page(
    @JsonProperty("page_name")
    String pageName,

    String title,

    String summary,

    String action,

    String sha,

    @JsonProperty("html_url")
    String htmlUrl
) {
}
