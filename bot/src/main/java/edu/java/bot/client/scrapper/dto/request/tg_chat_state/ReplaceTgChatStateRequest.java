package edu.java.bot.client.scrapper.dto.request.tg_chat_state;

import edu.java.bot.util.ScenarioDispatcher;

public record ReplaceTgChatStateRequest(
        ScenarioDispatcher.ScenarioType state
) {
}
