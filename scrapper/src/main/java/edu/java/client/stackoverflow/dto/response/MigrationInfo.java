package edu.java.client.stackoverflow.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.OffsetDateTime;
import lombok.Generated;

@Generated
public record MigrationInfo(
    @JsonProperty("on_date")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
    OffsetDateTime onDate,

    @JsonProperty("other_site")
    Site otherSite,

    @JsonProperty("question_id")
    int questionId
) {
}
