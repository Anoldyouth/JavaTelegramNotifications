package edu.java.bot.util.response.main;

import edu.java.bot.util.response.ResponseData;

public class UrlIsUntrackedResponse extends MainScenarioAbstractResponse {
    private final String link;

    public UrlIsUntrackedResponse(long chatId, String link) {
        super(chatId);

        this.link = link;
    }

    @Override
    @SuppressWarnings("MultipleStringLiterals")
    public ResponseData makeResponse() {
        String message = "Ссылка " + link + " успешно удалена из отслеживания.";

        return new ResponseData(createMessageRequest(message), getCommands(), true);
    }
}
