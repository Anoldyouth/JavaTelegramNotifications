package edu.java.controller;

import edu.java.dto.request.link.CreateLinkRequest;
import edu.java.dto.request.link.SearchLinksRequest;
import edu.java.dto.response.exception.ApiErrorResponse;
import edu.java.dto.response.exception.ValidationErrorsResponse;
import edu.java.dto.response.link.LinkResponse;
import edu.java.dto.response.link.OffsetPagination;
import edu.java.dto.response.link.SearchLinksResponse;
import edu.java.util.PaginationType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
@Tag(name = "links")
@RequestMapping("/links")
public class LinksController {
    @Operation(summary = "Поиск отслеживаемых ссылок")
    @ApiResponse(
            responseCode = "200",
            description = "Список найденных ссылок",
            content = @Content(schema = @Schema(implementation = SearchLinksResponse.class))
    )
    @ApiResponse(
            responseCode = "400",
            description = "Некорректные параметры",
            content = @Content(schema = @Schema(implementation = ValidationErrorsResponse.class))
    )
    @ApiResponse(
            responseCode = "500",
            description = "Ошибка сервера",
            content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))
    )
    @PostMapping("/links:search")
    public ResponseEntity<SearchLinksResponse> search(@Valid @RequestBody SearchLinksRequest request) {
        return ResponseEntity.ok(new SearchLinksResponse(List.of(), new OffsetPagination(
                PaginationType.OFFSET, 1, 1, 1
        )));
    }

    @Operation(summary = "Добавить отслеживание ссылки")
    @ApiResponse(
            responseCode = "200",
            description = "Ссылка успешно добавлена",
            content = @Content(schema = @Schema(implementation = LinkResponse.class))
    )
    @ApiResponse(
            responseCode = "400",
            description = "Некорректные параметры",
            content = @Content(schema = @Schema(implementation = ValidationErrorsResponse.class))
    )
    @ApiResponse(
            responseCode = "500",
            description = "Ошибка сервера",
            content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))
    )
    @PostMapping
    public ResponseEntity<LinkResponse> create(@RequestBody @Valid CreateLinkRequest request) {
        return ResponseEntity.ok(new LinkResponse(request.tgChatId(), request.link()));
    }

    @Operation(summary = "Убрать отслеживание ссылки")
    @ApiResponse(
            responseCode = "200",
            description = "Ссылка успешно удалена",
            content = @Content(schema = @Schema(implementation = LinkResponse.class))
    )
    @ApiResponse(
            responseCode = "400",
            description = "Некорректные параметры",
            content = @Content(schema = @Schema(implementation = ValidationErrorsResponse.class))
    )
    @ApiResponse(
            responseCode = "404",
            description = "Объект не найден",
            content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))
    )
    @ApiResponse(
            responseCode = "500",
            description = "Ошибка сервера",
            content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<LinkResponse> delete(@PathVariable @Positive long id) {
        return ResponseEntity.ok().body(new LinkResponse(id, "https://api.github.com"));
    }
}
