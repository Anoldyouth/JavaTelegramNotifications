package edu.java.client.stackoverflow.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Generated;

@Generated
public record Collective(
    String description,

    @JsonProperty("external_links")
    CollectiveExternalLink[] externalLinks,

    String link,

    String name,

    String slug,

    String[] tags
) {
}
