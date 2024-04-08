package edu.java.client.stackoverflow.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Generated;

@Generated
public record Collective(
    String description,

    @JsonProperty("external_links")
    List<CollectiveExternalLink> externalLinks,

    String link,

    String name,

    String slug,

    List<String> tags
) {
}
