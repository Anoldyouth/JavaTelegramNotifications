package edu.java.controller;

import edu.java.dto.response.exception.ApiErrorResponse;
import edu.java.dto.response.exception.TooManyRequestsResponse;
import edu.java.dto.response.exception.ValidationErrorsResponse;
import edu.java.service.TgChatService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
@Tag(name = "tg-chat")
@RequestMapping("/tg-chat/{id}")
@RequiredArgsConstructor
public class TgChatController {
    private final TgChatService tgChatService;

    @Operation(summary = "Зарегистрировать чат", operationId = "createTgChat")
    @ApiResponse(responseCode = "200", description = "Чат зарегистрирован")
    @ApiResponse(
            responseCode = "400",
            description = "Некорректные параметры",
            content = @Content(schema = @Schema(implementation = ValidationErrorsResponse.class))
    )
    @ApiResponse(
            responseCode = "429",
            description = "Слишком много запросов",
            content = @Content(schema = @Schema(implementation = TooManyRequestsResponse.class))
    )
    @ApiResponse(
            responseCode = "500",
            description = "Ошибка сервера",
            content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))
    )
    @PostMapping
    public void create(@PathVariable @Positive long id) {
        tgChatService.register(id);
    }

    @Operation(summary = "Удалить чат", operationId = "deleteTgChat")
    @ApiResponse(responseCode = "200", description = "Чат удален")
    @ApiResponse(
            responseCode = "400",
            description = "Некорректные параметры",
            content = @Content(schema = @Schema(implementation = ValidationErrorsResponse.class))
    )
    @ApiResponse(
            responseCode = "429",
            description = "Слишком много запросов",
            content = @Content(schema = @Schema(implementation = TooManyRequestsResponse.class))
    )
    @ApiResponse(
            responseCode = "500",
            description = "Ошибка сервера",
            content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))
    )
    @DeleteMapping
    public void delete(@PathVariable @Positive long id) {
        tgChatService.unregister(id);
    }
}
