package edu.java.bot.util.action;

import com.pengrad.telegrambot.model.Update;
import edu.java.bot.dto.response.ResponseData;
import edu.java.bot.dto.response.TrackResponse;
import edu.java.bot.util.CommandEnum;
import java.util.Optional;

public class TrackAction extends AbstractAction {
    @Override
    protected Optional<ResponseData> process(Update update) {
        if (update.message() == null) {
            return Optional.empty();
        }

        if (!update.message().text().equals(CommandEnum.TRACK.getCommand())) {
            return Optional.empty();
        }

        return Optional.of(new TrackResponse(update).makeResponse());
    }
}
