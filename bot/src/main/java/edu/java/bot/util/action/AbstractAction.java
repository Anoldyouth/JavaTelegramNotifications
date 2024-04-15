package edu.java.bot.util.action;

import com.pengrad.telegrambot.model.Update;
import edu.java.bot.util.response.ResponseData;
import edu.java.bot.util.response.UnknownCommandResponse;
import java.util.Optional;

public abstract class AbstractAction implements Action {
    private Action next;
    protected long chatId;

    @Override
    public final void setNext(Action next) {
        this.next = next;
    }

    @Override
    public ResponseData apply(Update update, long charId) {
        this.chatId = charId;

        return process(update).orElseGet(() -> {
            if (next == null) {
                return new UnknownCommandResponse(chatId).makeResponse();
            }

            return next.apply(update, chatId);
        });
    }

    protected abstract Optional<ResponseData> process(Update update);
}
