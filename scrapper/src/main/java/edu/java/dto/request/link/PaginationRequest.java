package edu.java.dto.request.link;

import edu.java.util.PaginationType;
import io.swagger.v3.oas.annotations.media.Schema;

public record PaginationRequest(
        @Schema(description = "Тип пагинации", example = "OFFSET", enumAsRef = true)
        PaginationType type,
        @Schema(description = "Смещение", example = "20", defaultValue = "10")
        long offset,
        @Schema(description = "Курсор", example = "yJpZCI6MTAsIl9wb2ludHNUb05leHRJdGVtcyI6dHJ1ZX0", nullable = true)
        String cursor,
        @Schema(description = "Лимит", example = "20")
        int limit
) {
}
