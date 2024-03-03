package edu.java.client.github.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Generated;

@JsonIgnoreProperties(ignoreUnknown = true)
@Generated
public record ReactionRollup(
    String url,

    @JsonProperty("total_count")
    int totalCount,

    @JsonProperty("+1")
    int plusOne,

    @JsonProperty("-1")
    int minusOne,

    int laugh,

    int confused,

    int heart,

    int hooray,

    int eyes,

    int rocket
) {
}
