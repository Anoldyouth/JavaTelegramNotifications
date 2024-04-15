package edu.java.bot.util.response;

import com.pengrad.telegrambot.request.BaseRequest;
import com.pengrad.telegrambot.request.SetMyCommands;
import com.pengrad.telegrambot.response.BaseResponse;
import org.jetbrains.annotations.NotNull;

public record ResponseData(
        @NotNull BaseRequest<? extends BaseRequest<?, ?>, ? extends BaseResponse> request,
        SetMyCommands menu,
        boolean deleteLastMessage
) {
}
