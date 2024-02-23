package edu.java.bot.util.action;

import com.pengrad.telegrambot.model.Update;
import edu.java.bot.dto.response.ListResponse;
import edu.java.bot.dto.response.ResponseData;
import edu.java.bot.util.CommandEnum;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
public class ListAction extends AbstractAction {
    @Override
    protected Optional<ResponseData> process(Update update) {
        if (update.message() == null) {
            return Optional.empty();
        }

        if (!update.message().text().equals(CommandEnum.LIST.getCommand())) {
            return Optional.empty();
        }

        return Optional.of(new ListResponse(update, List.of(
                "google.com",
                "yandex.ru",
                "github.com",
                "stackoverflow.com"
        )).makeResponse());
    }
}
