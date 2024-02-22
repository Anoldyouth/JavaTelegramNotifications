package edu.java.client.response.stack_overflow;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Collective {
    private String description;

    @JsonProperty("external_links")
    private CollectiveExternalLink[] externalLinks;

    private String link;

    private String name;

    private String slug;

    private String[] tags;
}
