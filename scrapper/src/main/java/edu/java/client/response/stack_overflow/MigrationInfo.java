package edu.java.client.response.stack_overflow;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.OffsetDateTime;
import lombok.Data;

@Data
public class MigrationInfo {
    @JsonProperty("on_date")
    private OffsetDateTime onDate;

    @JsonProperty("other_site")
    private Site otherSite;

    @JsonProperty("question_id")
    private int questionId;
}
