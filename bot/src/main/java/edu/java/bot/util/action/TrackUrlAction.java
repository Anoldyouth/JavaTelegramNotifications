package edu.java.bot.util.action;

import com.pengrad.telegrambot.model.Update;
import edu.java.bot.dto.response.ResponseData;
import edu.java.bot.dto.response.TrackUrlResponse;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Optional;

public class TrackUrlAction extends AbstractAction {
    @Override
    protected Optional<ResponseData> process(Update update) {
        if (update.message() == null) {
            return Optional.empty();
        }

        String messageText = update.message().text();

        if (!isUrl(messageText)) {
            return Optional.empty();
        }

        // TODO Добавить проверку соответствия ресурсам

        return Optional.of(new TrackUrlResponse(update, messageText).makeResponse());
    }

    private boolean isUrl(String message) {
        try {
            URL url = new URL(message);

            return true;
        } catch (MalformedURLException ignored) {
            return false;
        }
    }
}
