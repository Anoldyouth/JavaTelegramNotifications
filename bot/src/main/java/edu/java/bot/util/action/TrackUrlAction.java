package edu.java.bot.util.action;

import com.pengrad.telegrambot.model.Update;
import edu.java.bot.client.scrapper.ScrapperClient;
import edu.java.bot.client.scrapper.dto.request.link.CreateLinkRequest;
import edu.java.bot.client.scrapper.dto.request.tg_chat_state.ReplaceTgChatStateRequest;
import edu.java.bot.configuration.TrackedLinksConfiguration;
import edu.java.bot.util.ScenarioDispatcher;
import edu.java.bot.util.response.ResponseData;
import edu.java.bot.util.response.main.UrlIsTrackedResponse;
import edu.java.bot.util.response.track.UnsupportedLinkResponse;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TrackUrlAction extends AbstractAction {
    private final ScrapperClient scrapperClient;

    private final TrackedLinksConfiguration trackedLinksConfiguration;

    @Override
    protected Optional<ResponseData> process(Update update) {
        if (update.message() == null) {
            return Optional.empty();
        }

        String messageText = update.message().text();

        if (!isUrl(messageText)) {
            return Optional.empty();
        }

        if (!canBeTracked(messageText)) {
            return Optional.of(new UnsupportedLinkResponse(chatId).makeResponse());
        }

        var trackLinkRequest = new CreateLinkRequest(URI.create(messageText));
        scrapperClient.createLink(chatId, trackLinkRequest);

        var replaceStateRequest = new ReplaceTgChatStateRequest(ScenarioDispatcher.ScenarioType.MAIN);
        scrapperClient.replaceTgChatState(chatId, replaceStateRequest);

        return Optional.of(new UrlIsTrackedResponse(chatId, messageText).makeResponse());
    }

    private boolean isUrl(String message) {
        try {
            URL url = new URL(message);

            return true;
        } catch (MalformedURLException ignored) {
            return false;
        }
    }

    private boolean canBeTracked(String url) {
        for (String pattern: trackedLinksConfiguration.patterns()) {
            Matcher matcher = Pattern.compile(pattern).matcher(url);
            if (matcher.find()) {
                return true;
            }
        }

        return false;
    }
}
