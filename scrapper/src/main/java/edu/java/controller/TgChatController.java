package edu.java.controller;

import edu.java.dto.response.exception.ApiErrorResponse;
import edu.java.dto.response.exception.ValidationErrorsResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Positive;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
@Tag(name = "tg-chat")
public class TgChatController {
    @Operation(summary = "Зарегистрировать чат")
    @ApiResponse(responseCode = "200", description = "Чат зарегистрирован")
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
    @PostMapping("/tg-chat/{id}")
    public void create(@PathVariable @Positive long id) {
    }

    @Operation(summary = "Удалить чат")
    @ApiResponse(responseCode = "200", description = "Чат удален")
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
    @DeleteMapping("/tg-chat/{id}")
    public void delete(@PathVariable @Positive long id) {
    }
}
