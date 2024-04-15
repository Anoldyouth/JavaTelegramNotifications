package edu.java.bot.action;

import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import edu.java.bot.client.scrapper.ScrapperClient;
import edu.java.bot.client.scrapper.dto.request.link.SearchLinksRequest;
import edu.java.bot.client.scrapper.dto.request.tg_chat_state.ReplaceTgChatStateRequest;
import edu.java.bot.client.scrapper.dto.response.link.LinkResponse;
import edu.java.bot.client.scrapper.dto.response.link.OffsetPagination;
import edu.java.bot.client.scrapper.dto.response.link.SearchLinksResponse;
import edu.java.bot.configuration.properties.LinksListConfig;
import edu.java.bot.util.CommandEnum;
import edu.java.bot.util.ScenarioDispatcher;
import edu.java.bot.util.action.Action;
import edu.java.bot.util.action.UntrackAction;
import java.net.URI;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class UntrackActionUnitTest {
    Action action;
    ScrapperClient scrapperClientMock;
    LinksListConfig config;

    @BeforeEach
    void prepare() {
        scrapperClientMock = mock(ScrapperClient.class);
        config = new LinksListConfig(1);
        action = new UntrackAction(scrapperClientMock, config);
    }

    @Test
    void emptyLinksList() {
        var update = ActionHelper.makeMockUpdateMessage(CommandEnum.UNTRACK.getCommand());
        var searchResponse = mock(SearchLinksResponse.class);
        when(searchResponse.links()).thenReturn(List.of());
        when(scrapperClientMock.searchLinks(anyLong(), any(SearchLinksRequest.class))).thenReturn(searchResponse);

        var response = action.apply(update, 1);

        verify(scrapperClientMock, times(1)).searchLinks(
                1,
                new SearchLinksRequest(0L, 1L)
        );

        assertThat(response).isNotNull();
        assertTrue(((String) response.request().getParameters().get("text"))
                .startsWith("Список ссылок пуст."));
    }

    @Test
    void severalPages() {
        var update = ActionHelper.makeMockUpdateMessage(CommandEnum.UNTRACK.getCommand());
        var searchResponse = mock(SearchLinksResponse.class);
        var link = mock(LinkResponse.class);
        when(link.url()).thenReturn(URI.create("https://github.com/Anoldyouth/Java-Telegram-Notifications"));
        var pagination = mock(OffsetPagination.class);

        when(pagination.total()).thenReturn(2L);
        when(searchResponse.links()).thenReturn(List.of(link));
        when(searchResponse.pagination()).thenReturn(pagination);
        when(scrapperClientMock.searchLinks(anyLong(), any(SearchLinksRequest.class))).thenReturn(searchResponse);

        var response = action.apply(update, 1);

        verify(scrapperClientMock, times(1)).searchLinks(
                1,
                new SearchLinksRequest(0L, 1L)
        );
        verify(scrapperClientMock, times(1)).replaceTgChatState(
                1,
                new ReplaceTgChatStateRequest(ScenarioDispatcher.ScenarioType.UNTRACK_URL)
        );

        assertThat(response).isNotNull();
        assertTrue(((String) response.request().getParameters().get("text"))
                .startsWith("Выберите, для какой ссылки прекратить отслеживание"));
        assertThat(((InlineKeyboardMarkup) response.request().getParameters().get("reply_markup"))
                .inlineKeyboard().length).isEqualTo(2);
    }

    @Test
    void onePage() {
        var update = ActionHelper.makeMockUpdateMessage(CommandEnum.UNTRACK.getCommand());
        var searchResponse = mock(SearchLinksResponse.class);
        var link = mock(LinkResponse.class);
        when(link.url()).thenReturn(URI.create("https://github.com/Anoldyouth/Java-Telegram-Notifications"));
        var pagination = mock(OffsetPagination.class);

        when(pagination.total()).thenReturn(1L);
        when(searchResponse.links()).thenReturn(List.of(link));
        when(searchResponse.pagination()).thenReturn(pagination);
        when(scrapperClientMock.searchLinks(anyLong(), any(SearchLinksRequest.class))).thenReturn(searchResponse);

        var response = action.apply(update, 1);

        verify(scrapperClientMock, times(1)).searchLinks(
                1,
                new SearchLinksRequest(0L, 1L)
        );
        verify(scrapperClientMock, times(1)).replaceTgChatState(
                1,
                new ReplaceTgChatStateRequest(ScenarioDispatcher.ScenarioType.UNTRACK_URL)
        );

        assertThat(response).isNotNull();
        assertTrue(((String) response.request().getParameters().get("text"))
                .startsWith("Выберите, для какой ссылки прекратить отслеживание"));
        assertThat(((InlineKeyboardMarkup) response.request().getParameters().get("reply_markup"))
                .inlineKeyboard().length).isEqualTo(1);
    }

    @Test
    void anotherMessage() {
        var update = ActionHelper.makeMockUpdateMessage("test");

        var response = action.apply(update, 1);

        assertThat(response).isNotNull();
        assertTrue(((String) response.request().getParameters().get("text"))
                .startsWith("Неизвестная команда"));
    }

    @Test
    void anotherUpdateContent() {
        var update = ActionHelper.makeMockUpdateEmpty();

        var response = action.apply(update, 1);

        assertThat(response).isNotNull();
        assertTrue(((String) response.request().getParameters().get("text"))
                .startsWith("Неизвестная команда"));
    }
}
