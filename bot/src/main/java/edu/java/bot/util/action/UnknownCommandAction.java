package edu.java.bot.util.action;

import com.pengrad.telegrambot.model.Update;
import edu.java.bot.dto.response.ResponseData;
import edu.java.bot.dto.response.UnknownCommandResponse;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
public class UnknownCommandAction extends AbstractAction {
    @Override
    protected Optional<ResponseData> process(Update update) {
        return Optional.of(new UnknownCommandResponse(update).makeResponse());
    }
}
