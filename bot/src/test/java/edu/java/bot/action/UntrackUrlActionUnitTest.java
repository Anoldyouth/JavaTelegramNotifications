package edu.java.bot.action;

import edu.java.bot.client.scrapper.ScrapperClient;
import edu.java.bot.client.scrapper.dto.request.tg_chat_state.ReplaceTgChatStateRequest;
import edu.java.bot.client.scrapper.dto.response.link.LinkResponse;
import edu.java.bot.util.ScenarioDispatcher;
import edu.java.bot.util.action.Action;
import edu.java.bot.util.action.UntrackUrlAction;
import java.net.URI;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class UntrackUrlActionUnitTest {
    Action action;
    ScrapperClient scrapperClientMock;

    @BeforeEach
    void prepare() {
        scrapperClientMock = mock(ScrapperClient.class);
        action = new UntrackUrlAction(scrapperClientMock);
    }

    @Test
    void correctQuery() {
        var update = ActionHelper.makeMockUpdateCallBackQuery("1");
        var link = mock(LinkResponse.class);
        when(link.url()).thenReturn(URI.create("https://github.com/Anoldyouth/Java-Telegram-Notifications"));
        when(scrapperClientMock.deleteLink(anyLong(), anyLong())).thenReturn(link);

        var response = action.apply(update, 1);

        verify(scrapperClientMock, times(1)).deleteLink(1, 1);
        verify(scrapperClientMock, times(1)).replaceTgChatState(
                1,
                new ReplaceTgChatStateRequest(ScenarioDispatcher.ScenarioType.MAIN)
        );
        assertThat(response).isNotNull();
        Assertions.assertTrue(((String) response.request().getParameters().get("text"))
                .startsWith("Ссылка https://github.com/Anoldyouth/Java-Telegram-Notifications успешно удалена из отслеживания."));
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
