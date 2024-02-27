package edu.java.dto.response.link;

import io.swagger.v3.oas.annotations.media.Schema;
import org.jetbrains.annotations.NotNull;

public record SearchLinksResponse(
        @NotNull
        @Schema(description = "Список найденных ссылок")
        LinkResponse[] links,

        @NotNull
        @Schema(description = "Пагинация", oneOf = {OffsetPagination.class, CursorPagination.class})
        Pagination pagination
) {
}
