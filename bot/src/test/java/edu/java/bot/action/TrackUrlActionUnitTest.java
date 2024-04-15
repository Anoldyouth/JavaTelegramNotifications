package edu.java.bot.action;

import edu.java.bot.client.scrapper.ScrapperClient;
import edu.java.bot.client.scrapper.dto.request.link.CreateLinkRequest;
import edu.java.bot.client.scrapper.dto.request.tg_chat_state.ReplaceTgChatStateRequest;
import edu.java.bot.configuration.TrackedLinksConfiguration;
import edu.java.bot.util.ScenarioDispatcher;
import edu.java.bot.util.action.Action;
import edu.java.bot.util.action.TrackUrlAction;
import java.net.URI;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class TrackUrlActionUnitTest {
    Action action;
    ScrapperClient scrapperClientMock;
    TrackedLinksConfiguration config = new TrackedLinksConfiguration();

    @BeforeEach
    void prepare() {
        scrapperClientMock = mock(ScrapperClient.class);
        action = new TrackUrlAction(scrapperClientMock, config);
    }

    @Test
    void correctUrl() {
        URI url = URI.create("https://github.com/Anoldyouth/Java-Telegram-Notifications");
        var update = ActionHelper.makeMockUpdateMessage(
                url.toString()
        );

        var response = action.apply(update, 1);

        verify(scrapperClientMock, times(1)).createLink(1, new CreateLinkRequest(url));
        verify(scrapperClientMock, times(1)).replaceTgChatState(
                1,
                new ReplaceTgChatStateRequest(ScenarioDispatcher.ScenarioType.MAIN)
        );
        assertThat(response).isNotNull();
        Assertions.assertTrue(((String) response.request().getParameters().get("text"))
                .startsWith("Ссылка " + url + " успешно добавлена для отслеживания."));
    }

    @Test
    void notUrl() {
        var update = ActionHelper.makeMockUpdateMessage("test");

        var response = action.apply(update, 1);

        assertThat(response).isNotNull();
        Assertions.assertTrue(((String) response.request().getParameters().get("text"))
                .startsWith("Неизвестная команда"));
    }

    @Test
    void incorrectUrl() {
        var update = ActionHelper.makeMockUpdateMessage("https://www.google.com");

        var response = action.apply(update, 1);

        assertThat(response).isNotNull();
        Assertions.assertTrue(((String) response.request().getParameters().get("text"))
                .startsWith("Данная ссылка не поддерживается ботом"));
    }

    @Test
    void anotherUpdateContent() {
        var update = ActionHelper.makeMockUpdateEmpty();

        var response = action.apply(update, 1);

        assertThat(response).isNotNull();
        Assertions.assertTrue(((String) response.request().getParameters().get("text"))
                .startsWith("Неизвестная команда"));
    }
}
