package edu.java.client.response.stack_overflow;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class OriginalQuestion {
    @JsonProperty("accepted_answer_id")
    private int acceptedAnswerId;

    @JsonProperty("answer_count")
    private int answerCount;

    @JsonProperty("question_id")
    private int questionId;

    private String title;
}
