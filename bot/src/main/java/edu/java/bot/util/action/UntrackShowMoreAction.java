package edu.java.bot.util.action;

import com.pengrad.telegrambot.model.Update;
import edu.java.bot.client.scrapper.ScrapperClient;
import edu.java.bot.client.scrapper.dto.request.link.SearchLinksRequest;
import edu.java.bot.client.scrapper.dto.request.tg_chat_state.ReplaceTgChatStateRequest;
import edu.java.bot.configuration.properties.LinksListConfig;
import edu.java.bot.util.ScenarioDispatcher;
import edu.java.bot.util.response.ResponseData;
import edu.java.bot.util.response.main.EmptyLinksListResponse;
import edu.java.bot.util.response.untrack.UntrackResponse;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UntrackShowMoreAction extends AbstractAction {
    private final ScrapperClient scrapperClient;

    private final LinksListConfig linksListConfig;

    @Override
    @SuppressWarnings("MultipleStringLiterals")
    protected Optional<ResponseData> process(Update update) {
        if (update.callbackQuery() == null) {
            return Optional.empty();
        }

        if (!update.callbackQuery().data().startsWith("offset_")) {
            return Optional.empty();
        }

        long offset = Long.parseLong(update.callbackQuery().data().replace("offset_", ""));

        var searchLinksRequest = new SearchLinksRequest(offset, linksListConfig.limit());
        var response = scrapperClient.searchLinks(chatId, searchLinksRequest);

        // Если что-то пошло не так и список пуст - сбрасываем состояние
        if (response.links().isEmpty()) {
            var replaceStateRequest = new ReplaceTgChatStateRequest(ScenarioDispatcher.ScenarioType.MAIN);
            scrapperClient.replaceTgChatState(chatId, replaceStateRequest);

            return Optional.of(new EmptyLinksListResponse(chatId, true).makeResponse());
        }

        Long previousOffset = null;
        Long nextOffset = null;

        if ((offset - linksListConfig.limit()) >= 0) {
            previousOffset = offset - linksListConfig.limit();
        }

        if (response.pagination().total() > (offset + linksListConfig.limit())) {
            nextOffset = offset + linksListConfig.limit();
        }

        return Optional.of(new UntrackResponse(
                chatId,
                response.links(),
                true,
                previousOffset,
                nextOffset
        ).makeResponse());
    }
}
