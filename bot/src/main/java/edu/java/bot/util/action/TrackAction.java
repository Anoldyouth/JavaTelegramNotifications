package edu.java.bot.util.action;

import com.pengrad.telegrambot.model.Update;
import edu.java.bot.client.scrapper.ScrapperClient;
import edu.java.bot.client.scrapper.dto.request.tg_chat_state.ReplaceTgChatStateRequest;
import edu.java.bot.util.CommandEnum;
import edu.java.bot.util.ScenarioDispatcher;
import edu.java.bot.util.response.ResponseData;
import edu.java.bot.util.response.track.TrackResponse;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TrackAction extends AbstractAction {
    private final ScrapperClient scrapperClient;

    @Override
    protected Optional<ResponseData> process(Update update) {
        if (update.message() == null) {
            return Optional.empty();
        }

        if (!update.message().text().equals(CommandEnum.TRACK.getCommand())) {
            return Optional.empty();
        }

        var request = new ReplaceTgChatStateRequest(ScenarioDispatcher.ScenarioType.TRACK_URL);
        scrapperClient.replaceTgChatState(chatId, request);

        return Optional.of(new TrackResponse(chatId).makeResponse());
    }
}
