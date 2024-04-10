package edu.java.client.stackoverflow.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;
import lombok.Generated;

@JsonIgnoreProperties(ignoreUnknown = true)
@Generated
public record Response(
    List<Question> items
) {
}
