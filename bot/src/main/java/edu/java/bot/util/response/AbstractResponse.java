package edu.java.bot.util.response;

import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.request.SetMyCommands;

public abstract class AbstractResponse {
    protected final long chatId;

    @SuppressWarnings("HiddenField")
    public AbstractResponse(long chatId) {
        this.chatId = chatId;
    }

    abstract public ResponseData makeResponse();

    abstract public SetMyCommands getCommands();

    protected SendMessage createMessageRequest(String message) {
        return new SendMessage(chatId, message)
                .disableWebPagePreview(true)
                .parseMode(ParseMode.Markdown);
    }
}
