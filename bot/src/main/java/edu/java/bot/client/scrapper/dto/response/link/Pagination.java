package edu.java.bot.client.scrapper.dto.response.link;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.EXISTING_PROPERTY,
        property = "type", visible = true
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = OffsetPagination.class, name = "OFFSET"),
        @JsonSubTypes.Type(value = CursorPagination.class, name = "CURSOR")
})
public class Pagination {
    private String type;
}
