package edu.java.bot.util.action;

import com.pengrad.telegrambot.model.Update;
import edu.java.bot.client.scrapper.ScrapperClient;
import edu.java.bot.client.scrapper.dto.request.tg_chat_state.ReplaceTgChatStateRequest;
import edu.java.bot.client.scrapper.dto.response.link.LinkResponse;
import edu.java.bot.exception.ApiException;
import edu.java.bot.util.ScenarioDispatcher;
import edu.java.bot.util.response.ResponseData;
import edu.java.bot.util.response.main.UrlIsUntrackedResponse;
import edu.java.bot.util.response.main.UrlNotFoundResponse;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UntrackUrlAction extends AbstractAction {
    private final ScrapperClient scrapperClient;

    @Override
    protected Optional<ResponseData> process(Update update) {
        if (update.callbackQuery() == null) {
            return Optional.empty();
        }

        long linkId;
        try {
            linkId = Long.parseLong(update.callbackQuery().data());
        } catch (Exception ignored) {
            return Optional.empty();
        }


        try {
            LinkResponse response = scrapperClient.deleteLink(chatId, linkId);

            var request = new ReplaceTgChatStateRequest(ScenarioDispatcher.ScenarioType.MAIN);
            scrapperClient.replaceTgChatState(chatId, request);

            return Optional.of(new UrlIsUntrackedResponse(chatId, response.url().toString()).makeResponse());
        } catch (Exception exception) {
            var cause = exception.getCause();

            if (!(cause instanceof ApiException) || ((ApiException) cause).getCode() != HttpStatus.NOT_FOUND.value()) {
                throw exception;
            }
        }

        // В крайнем случае, если что-то сломалось и ссылка уже была удалена
        var request = new ReplaceTgChatStateRequest(ScenarioDispatcher.ScenarioType.MAIN);
        scrapperClient.replaceTgChatState(chatId, request);

        return Optional.of(new UrlNotFoundResponse(chatId).makeResponse());
    }
}
