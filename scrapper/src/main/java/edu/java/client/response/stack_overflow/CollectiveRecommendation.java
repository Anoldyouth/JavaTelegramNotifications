package edu.java.client.response.stack_overflow;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.OffsetDateTime;
import lombok.Data;

@Data
public class CollectiveRecommendation {
    private Collective collective;

    @JsonProperty("creation_date")
    private OffsetDateTime creationDate;
}
