package edu.java.bot;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.request.SetMyCommands;
import com.pengrad.telegrambot.request.SetWebhook;
import edu.java.bot.util.action.Action;
import edu.java.bot.util.action.ActionFacade;
import edu.java.bot.util.response.ResponseData;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ActionFacadeTest {
    @Test
    @DisplayName("Проверка выполнения")
    void chain() {
        Action testAction = mock(Action.class);
        when(testAction.apply(any(Update.class))).thenReturn(new ResponseData(new SendMessage(1L, "test"), new SetMyCommands()));

        ActionFacade actionFacade = new ActionFacade();

        var response = actionFacade.applyScenario(List.of(testAction), new Update());

        assertThat(response).isNotNull();
    }

    @Test
    @DisplayName("Сохранение порядка")
    void actionsOrder() {
        Action firstAction = mock(Action.class);
        when(firstAction.apply(any(Update.class))).thenReturn(new ResponseData(new SetWebhook(), new SetMyCommands()));

        Action secondAction = mock(Action.class);
        when(secondAction.apply(any(Update.class))).thenReturn(new ResponseData(new SendMessage(1L, "test"), new SetMyCommands()));

        ActionFacade actionFacade = new ActionFacade();

        var response = actionFacade.applyScenario(List.of(firstAction, secondAction), new Update());

        assertThat(response.request()).isInstanceOf(SetWebhook.class);
    }
}
