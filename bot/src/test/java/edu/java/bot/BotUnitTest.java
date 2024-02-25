package edu.java.bot;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.BaseRequest;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.request.SetMyCommands;
import edu.java.bot.service.Bot;
import edu.java.bot.util.ScenarioDispatcher;
import edu.java.bot.util.action.Action;
import edu.java.bot.util.action.ActionFacade;
import edu.java.bot.util.response.ResponseData;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import org.mockito.Captor;
import org.mockito.Mockito;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class BotUnitTest {
    Bot bot;
    TelegramBot telegramBotMock = mock(TelegramBot.class);
    ScenarioDispatcher scenarioDispatcherMock = mock(ScenarioDispatcher.class);
    ActionFacade actionFacadeMock = mock(ActionFacade.class);

    @Captor
    ArgumentCaptor<BaseRequest<?, ?>> captor;

    @BeforeEach
    void startBot() {
        Action action = mock(Action.class);

        when(scenarioDispatcherMock.getScenario(Mockito.any(ScenarioDispatcher.ScenarioType.class)))
                .thenReturn(List.of(action));

        when(actionFacadeMock.applyScenario(anyList(), any(Update.class)))
                .thenReturn(new ResponseData(new SendMessage(1L, "test"), new SetMyCommands()));

        bot = new Bot(telegramBotMock, scenarioDispatcherMock, actionFacadeMock);
        bot.start();
    }

    @Test
    void responseSent() {
        List<Update> updates = List.of(mock(Update.class));

        int result = bot.process(updates);
        verify(telegramBotMock, times(2)).execute(captor.capture());
        List<BaseRequest<?, ?>> capturedArguments = captor.getAllValues();

        assertThat(capturedArguments.get(0)).isInstanceOf(SendMessage.class);
        assertThat(capturedArguments.get(1)).isInstanceOf(SetMyCommands.class);
        assertThat(result).isEqualTo(UpdatesListener.CONFIRMED_UPDATES_ALL);
    }
}
