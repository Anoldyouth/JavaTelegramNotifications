package edu.java.bot.client.scrapper.dto.response.tg_chat_state;

import edu.java.bot.util.ScenarioDispatcher;

public record TgChatStateResponse(
        long tgChatId,
        ScenarioDispatcher.ScenarioType state
) {
}
