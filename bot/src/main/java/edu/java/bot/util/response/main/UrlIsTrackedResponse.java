package edu.java.bot.util.response.main;

import edu.java.bot.util.response.ResponseData;

public class UrlIsTrackedResponse extends MainScenarioAbstractResponse {

    private final String url;

    public UrlIsTrackedResponse(long chatId, String url) {
        super(chatId);

        this.url = url;
    }

    @Override
    @SuppressWarnings("MultipleStringLiterals")
    public ResponseData makeResponse() {
        String message = "Ссылка " + url + " успешно добавлена для отслеживания.";

        return new ResponseData(createMessageRequest(message), getCommands(), false);
    }
}
