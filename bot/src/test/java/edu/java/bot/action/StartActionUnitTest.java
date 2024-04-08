package edu.java.bot.action;

import edu.java.bot.client.scrapper.ScrapperClient;
import edu.java.bot.util.CommandEnum;
import edu.java.bot.util.action.Action;
import edu.java.bot.util.action.StartAction;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class StartActionUnitTest {
    Action action;
    ScrapperClient scrapperClientMock;

    @BeforeEach
    void prepare() {
        scrapperClientMock = mock(ScrapperClient.class);
        action = new StartAction(scrapperClientMock);
    }

    @Test
    void correctCommand() {
        var update = ActionHelper.makeMockUpdateMessage(CommandEnum.START.getCommand());

        var response = action.apply(update, 1);

        verify(scrapperClientMock, times(1)).createTgChat(1);
        assertThat(response).isNotNull();
        Assertions.assertTrue(((String) response.request().getParameters().get("text")).startsWith("Привет! Этот Бот"));
    }

    @Test
    void anotherMessage() {
        var update = ActionHelper.makeMockUpdateMessage("test");

        var response = action.apply(update, 1);

        assertThat(response).isNotNull();
        Assertions.assertTrue(((String) response.request().getParameters().get("text"))
                .startsWith("Неизвестная команда"));
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
