package edu.java.client.response.stack_overflow;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.OffsetDateTime;
import lombok.Data;

@Data
public class Notice {
    private String body;

    @JsonProperty("creation_date")
    private OffsetDateTime creationDate;

    @JsonProperty("owner_user_id")
    private Integer ownerUserId;
}
