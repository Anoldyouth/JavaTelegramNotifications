package edu.java.client.response.github;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ReactionRollup {
    private String url;

    @JsonProperty("total_count")
    private int totalCount;

    @JsonProperty("+1")
    private int plusOne;

    @JsonProperty("-1")
    private int minusOne;

    private int laugh;

    private int confused;

    private int heart;

    private int hooray;

    private int eyes;

    private int rocket;
}
