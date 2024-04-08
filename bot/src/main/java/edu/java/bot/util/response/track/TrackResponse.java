package edu.java.bot.util.response.track;

import edu.java.bot.util.response.ResponseData;

public class TrackResponse extends TrackUrlScenarioAbstractResponse {
    public TrackResponse(long chatId) {
        super(chatId);
    }

    @Override
    public ResponseData makeResponse() {
        String message = "Введите ссылку для отслеживания.\n"
                + "Помните, что бот на данный момент умеет отслеживать только GitHub и StackOverFlow.";

        return new ResponseData(createMessageRequest(message), getCommands(), false);
    }
}
