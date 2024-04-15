package edu.java.bot.util.response.track;

import edu.java.bot.util.response.ResponseData;

public class UnsupportedLinkResponse extends TrackUrlScenarioAbstractResponse {
    public UnsupportedLinkResponse(long chatId) {
        super(chatId);
    }

    @Override
    public ResponseData makeResponse() {
        String message = "Данная ссылка не поддерживается ботом. Попробуйте ввести другую ссылку.";

        return new ResponseData(
                createMessageRequest(message),
                getCommands(),
                false
        );
    }
}
