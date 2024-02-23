package edu.java.bot.util.action;

import com.pengrad.telegrambot.model.Update;
import edu.java.bot.dto.response.ResponseData;
import edu.java.bot.dto.response.UntrackUrlResponse;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
public class UntrackUrlAction extends AbstractAction {
    @Override
    protected Optional<ResponseData> process(Update update) {
        if (update.callbackQuery() == null) {
            return Optional.empty();
        }

        // TODO добавить удаление
        String url = update.callbackQuery().data();

        return Optional.ofNullable(new UntrackUrlResponse(update, url).makeResponse());
    }
}
