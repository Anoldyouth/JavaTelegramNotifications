package edu.java.dto.response.link;

import edu.java.util.PaginationType;
import io.swagger.v3.oas.annotations.media.Schema;

public record CursorPagination(
        @Schema(description = "Тип пагинации", example = "CURSOR", enumAsRef = true)
        PaginationType type,
        @Schema(description = "Курсор", example = "yJpZCI6MTAsIl9wb2ludHNUb05leHRJdGVtcyI6dHJ1ZX0", nullable = true)
        String cursor,
        @Schema(
                description = "Курсор назад",
                example = "yJpZCI6MTAsIl9wb2ludHNUb05leHRJdGVtcyI6dHJ1ZX0",
                nullable = true
        )
        String previousCursor,
        @Schema(
                description = "Курсор вперед",
                example = "yJpZCI6MTAsIl9wb2ludHNUb05leHRJdGVtcyI6dHJ1ZX0",
                nullable = true
        )
        String nextCursor,
        @Schema(description = "Лимит", example = "20")
        int limit
) implements Pagination {
}
