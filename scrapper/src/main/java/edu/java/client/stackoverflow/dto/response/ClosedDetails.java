package edu.java.client.stackoverflow.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Generated;

@Generated
public record ClosedDetails(
    @JsonProperty("by_users")
    ShallowUser[] byUsers,

    String description,

    @JsonProperty("on_hold")
    boolean onHold,

    @JsonProperty("original_questions")
    OriginalQuestion[] originalQuestions,

    String reason
) {
}
