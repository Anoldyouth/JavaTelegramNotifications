package edu.java.bot.util.response.main;

import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.util.response.ResponseData;

public class EmptyLinksListResponse extends MainScenarioAbstractResponse {
    private final boolean deletePreviousMessage;

    public EmptyLinksListResponse(long chatId, boolean deletePreviousMessage) {
        super(chatId);

        this.deletePreviousMessage = deletePreviousMessage;
    }

    @Override
    public ResponseData makeResponse() {
        var response = new SendMessage(chatId, "Список ссылок пуст.")
                .disableWebPagePreview(true);

        return new ResponseData(
                response,
                getCommands(),
                deletePreviousMessage
        );
    }
}
