package edu.java.client.stackoverflow.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Generated;

@Generated
public record ClosedDetails(
    @JsonProperty("by_users")
    List<ShallowUser> byUsers,

    String description,

    @JsonProperty("on_hold")
    boolean onHold,

    @JsonProperty("original_questions")
    List<OriginalQuestion> originalQuestions,

    String reason
) {
}
