package edu.java.client.stackoverflow.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Generated;

@Generated
public record OriginalQuestion(
    @JsonProperty("accepted_answer_id")
    int acceptedAnswerId,

    @JsonProperty("answer_count")
    int answerCount,

    @JsonProperty("question_id")
    int questionId,

    String title
) {
}
