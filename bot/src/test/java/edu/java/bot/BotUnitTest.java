package edu.java.bot;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.CallbackQuery;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.User;
import com.pengrad.telegrambot.request.BaseRequest;
import com.pengrad.telegrambot.request.DeleteMessage;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.request.SetMyCommands;
import edu.java.bot.client.scrapper.ScrapperClient;
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
import static org.mockito.ArgumentMatchers.anyLong;
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

    ScrapperClient scrapperClient = mock(ScrapperClient.class);

    @Captor
    ArgumentCaptor<BaseRequest<?, ?>> captor;

    @BeforeEach
    void startBot() {
        Action action = mock(Action.class);

        when(scenarioDispatcherMock.getScenario(Mockito.any(ScenarioDispatcher.ScenarioType.class)))
                .thenReturn(List.of(action));

        when(actionFacadeMock.applyScenario(anyList(), any(Update.class), anyLong()))
                .thenReturn(new ResponseData(new SendMessage(1L, "test"), new SetMyCommands(), true));

        bot = new Bot(telegramBotMock, scenarioDispatcherMock, actionFacadeMock, scrapperClient);
        bot.start();
    }

    @Test
    void responseSent() {
        Update updateMock = mock(Update.class);
        CallbackQuery callbackQueryMock = mock(CallbackQuery.class);
        Message messageMock = mock(Message.class);
        User userMock = mock(User.class);

        when(messageMock.messageId()).thenReturn(1);
        when(userMock.id()).thenReturn(1L);
        when(callbackQueryMock.message()).thenReturn(messageMock);
        when(callbackQueryMock.from()).thenReturn(userMock);
        when(updateMock.callbackQuery()).thenReturn(callbackQueryMock);

        List<Update> updates = List.of(updateMock);

        int result = bot.process(updates);
        verify(telegramBotMock, times(3)).execute(captor.capture());
        List<BaseRequest<?, ?>> capturedArguments = captor.getAllValues();

        assertThat(capturedArguments.get(0)).isInstanceOf(DeleteMessage.class);
        assertThat(capturedArguments.get(1)).isInstanceOf(SendMessage.class);
        assertThat(capturedArguments.get(2)).isInstanceOf(SetMyCommands.class);
        assertThat(result).isEqualTo(UpdatesListener.CONFIRMED_UPDATES_ALL);
    }
}
