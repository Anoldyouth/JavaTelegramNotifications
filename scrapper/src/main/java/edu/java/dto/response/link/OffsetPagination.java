package edu.java.dto.response.link;

import edu.java.util.PaginationType;
import io.swagger.v3.oas.annotations.media.Schema;

public record OffsetPagination(
        @Schema(description = "Тип пагинации", example = "OFFSET", enumAsRef = true)
        PaginationType type,
        @Schema(description = "Смещение", example = "20")
        long offset,
        @Schema(description = "Всего найдено", example = "20")
        int total,
        @Schema(description = "Лимит", example = "20")
        int limit
) implements Pagination {
}
