package edu.java.bot.util.response;

import com.pengrad.telegrambot.request.SetMyCommands;

public class UnknownCommandResponse extends AbstractResponse {
    public UnknownCommandResponse(long chatId) {
        super(chatId);
    }

    @Override
    public ResponseData makeResponse() {
        String message = "Неизвестная команда.";

        return new ResponseData(createMessageRequest(message), getCommands(), false);
    }

    @Override
    public SetMyCommands getCommands() {
        return null;
    }
}
