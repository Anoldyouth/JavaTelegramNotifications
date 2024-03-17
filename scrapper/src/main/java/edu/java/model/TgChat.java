package edu.java.model;

import edu.java.util.State;
import java.time.OffsetDateTime;

public record TgChat(
        long id,
        State state,
        OffsetDateTime createdAt
) {
}
