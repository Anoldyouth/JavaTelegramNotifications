package edu.java.dto.response.link;

import io.swagger.v3.oas.annotations.media.Schema;

public record OffsetPagination(
        @Schema(description = "Текущее смещение")
        long offset,

        @Schema(description = "Всего записей")
        long total,

        @Schema(description = "Лимит записей")
        long limit
) {
}
