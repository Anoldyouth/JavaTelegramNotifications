package edu.java.bot.util.response.main;

import edu.java.bot.util.response.ResponseData;

public class UrlNotFoundResponse extends MainScenarioAbstractResponse {
    public UrlNotFoundResponse(long chatId) {
        super(chatId);
    }

    @Override
    @SuppressWarnings("MultipleStringLiterals")
    public ResponseData makeResponse() {
        String message = "Выбранная ссылка была уже ранее удалена.";

        return new ResponseData(createMessageRequest(message), getCommands(), true);
    }
}
