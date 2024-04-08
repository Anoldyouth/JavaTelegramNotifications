package edu.java.bot.util.action;

import com.pengrad.telegrambot.model.Update;
import edu.java.bot.client.scrapper.ScrapperClient;
import edu.java.bot.client.scrapper.dto.request.link.SearchLinksRequest;
import edu.java.bot.client.scrapper.dto.request.tg_chat_state.ReplaceTgChatStateRequest;
import edu.java.bot.configuration.properties.LinksListConfig;
import edu.java.bot.util.CommandEnum;
import edu.java.bot.util.ScenarioDispatcher;
import edu.java.bot.util.response.ResponseData;
import edu.java.bot.util.response.main.EmptyLinksListResponse;
import edu.java.bot.util.response.untrack.UntrackResponse;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UntrackAction extends AbstractAction {
    private final ScrapperClient scrapperClient;
    private final LinksListConfig linksListConfig;

    @Override
    @SuppressWarnings("ReturnCount")
    protected Optional<ResponseData> process(Update update) {
        if (update.message() == null) {
            return Optional.empty();
        }

        if (!update.message().text().equals(CommandEnum.UNTRACK.getCommand())) {
            return Optional.empty();
        }

        var searchLinksRequest = new SearchLinksRequest(0L, linksListConfig.limit());
        var response = scrapperClient.searchLinks(chatId, searchLinksRequest);

        if (response.links().isEmpty()) {
            return Optional.of(new EmptyLinksListResponse(chatId, false).makeResponse());
        }

        var replaceStateRequest = new ReplaceTgChatStateRequest(ScenarioDispatcher.ScenarioType.UNTRACK_URL);
        scrapperClient.replaceTgChatState(chatId, replaceStateRequest);

        if (response.pagination().total() > linksListConfig.limit()) {
            return Optional.of(new UntrackResponse(
                    chatId,
                    response.links(),
                    false,
                    null,
                    linksListConfig.limit()
            ).makeResponse());
        }

        return Optional.of(new UntrackResponse(
                chatId,
                response.links(),
                false
        ).makeResponse());
    }
}
