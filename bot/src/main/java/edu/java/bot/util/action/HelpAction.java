package edu.java.bot.util.action;

import com.pengrad.telegrambot.model.Update;
import edu.java.bot.util.response.HelpResponse;
import edu.java.bot.util.response.ResponseData;
import edu.java.bot.util.CommandEnum;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
public class HelpAction extends AbstractAction {
    @Override
    protected Optional<ResponseData> process(Update update) {
        if (update.message() == null) {
            return Optional.empty();
        }

        if (!update.message().text().equals(CommandEnum.HELP.getCommand())) {
            return Optional.empty();
        }

        return Optional.of(new HelpResponse(update).makeResponse());
    }
}
