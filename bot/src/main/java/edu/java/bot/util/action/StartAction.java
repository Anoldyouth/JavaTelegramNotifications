package edu.java.bot.util.action;

import com.pengrad.telegrambot.model.Update;
import edu.java.bot.client.scrapper.ScrapperClient;
import edu.java.bot.util.CommandEnum;
import edu.java.bot.util.response.ResponseData;
import edu.java.bot.util.response.main.StartResponse;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StartAction extends AbstractAction {
    private final ScrapperClient scrapperClient;

    @Override
    protected Optional<ResponseData> process(Update update) {
        if (update.message() == null) {
            return Optional.empty();
        }

        if (!update.message().text().equals(CommandEnum.START.getCommand())) {
            return Optional.empty();
        }

        scrapperClient.createTgChat(chatId);

        return Optional.of(new StartResponse(chatId).makeResponse());
    }
}
