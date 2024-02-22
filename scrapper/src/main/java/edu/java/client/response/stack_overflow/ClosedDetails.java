package edu.java.client.response.stack_overflow;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ClosedDetails {
    @JsonProperty("by_users")
    private ShallowUser[] byUsers;

    private String description;

    @JsonProperty("on_hold")
    private boolean onHold;

    @JsonProperty("original_questions")
    private OriginalQuestion[] originalQuestions;

    private String reason;
}
