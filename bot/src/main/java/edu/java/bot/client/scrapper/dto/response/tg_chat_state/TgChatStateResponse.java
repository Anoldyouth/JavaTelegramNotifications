package edu.java.bot.client.scrapper.dto.response.tg_chat_state;

public record TgChatStateResponse(
        long tgChatId,
        short state
) {
}
