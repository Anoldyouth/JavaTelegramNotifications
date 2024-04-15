package edu.java.bot.controller;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.dto.request.SendUpdateRequest;
import edu.java.bot.dto.response.ApiErrorResponse;
import edu.java.bot.dto.response.ValidationErrorsResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
@Tag(name = "updates")
@RequestMapping("/updates")
@RequiredArgsConstructor
public class UpdateController {
    private final TelegramBot telegramBot;

    @Operation(summary = "Отправить обновление", operationId = "sendUpdate")
    @ApiResponse(responseCode = "200", description = "Обновление отработано")
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
    public void update(@RequestBody @Valid SendUpdateRequest request) {
        String message = "Получено новое событие для ссылки " + request.url() + "\n\n" + request.description();

        for (long chatId: request.tgChatIds()) {
            var botRequest = new SendMessage(chatId, message)
                    .disableWebPagePreview(true)
                    .parseMode(ParseMode.Markdown);

            telegramBot.execute(botRequest);
        }
    }
}
