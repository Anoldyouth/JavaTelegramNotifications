package edu.java.dto.response.link;

import io.swagger.v3.oas.annotations.media.Schema;

public record OffsetPagination(
        @Schema(description = "Номер страницы")
        int page,

        @Schema(description = "Всего страниц")
        int pageCount,

        @Schema(description = "Всего записей")
        int total,

        @Schema(description = "Лимит записей")
        int limit
) {
}
