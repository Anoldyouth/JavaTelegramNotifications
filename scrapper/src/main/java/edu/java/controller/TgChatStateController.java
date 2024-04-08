package edu.java.controller;

import edu.java.dto.request.tg_chat_state.ReplaceTgChatStateRequest;
import edu.java.dto.response.exception.ApiErrorResponse;
import edu.java.dto.response.exception.ValidationErrorsResponse;
import edu.java.dto.response.tg_chat_state.TgChatStateResponse;
import edu.java.exception.NotFoundException;
import edu.java.model.TgChat;
import edu.java.service.TgChatService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
@Tag(name = "tg-chat-state")
@RequestMapping("/tg-chat/{id}/state")
@RequiredArgsConstructor
public class TgChatStateController {
    private final TgChatService tgChatService;

    @Operation(summary = "Заменить состояние чата", operationId = "replaceTgChatState")
    @ApiResponse(
            responseCode = "200",
            description = "Состояние сохранено",
            content = @Content(schema = @Schema(implementation = TgChatStateResponse.class))
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
    @PutMapping
    public ResponseEntity<TgChatStateResponse> replace(
            @PathVariable @Positive long id,
            @RequestBody @Valid ReplaceTgChatStateRequest request
    ) throws NotFoundException {
        TgChat chat = tgChatService.updateState(id, request.state());

        return ResponseEntity.ok().body(new TgChatStateResponse(chat.id(), chat.state()));
    }

    @Operation(summary = "Получить состояние чата", operationId = "getTgChatState")
    @ApiResponse(
            responseCode = "200",
            description = "Состояние получено",
            content = @Content(schema = @Schema(implementation = TgChatStateResponse.class))
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
    @GetMapping
    public ResponseEntity<TgChatStateResponse> get(@PathVariable @Positive long id) throws NotFoundException {
        TgChat chat = tgChatService.get(id);

        return ResponseEntity.ok().body(new TgChatStateResponse(chat.id(), chat.state()));
    }
}
