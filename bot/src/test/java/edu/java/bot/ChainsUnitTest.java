package edu.java.bot;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.request.SetMyCommands;
import com.pengrad.telegrambot.request.SetWebhook;
import edu.java.bot.dto.response.ResponseData;
import edu.java.bot.util.Chain;
import edu.java.bot.util.action.AbstractAction;
import edu.java.bot.util.action.Action;
import edu.java.bot.util.action.ActionFacade;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ChainsUnitTest {
    @Test
    @DisplayName("Проверка выполнения")
    void chain() {
        Action testAction = new AbstractAction() {
            @Override
            protected Optional<ResponseData> process(Update update) {
                return Optional.of(new ResponseData(new SendMessage(1L, "test"), new SetMyCommands()));
            }
        };

        ActionFacade actionFacade = new ActionFacade(new Chain(testAction).getChain());

        var response = actionFacade.apply(new Update());

        assertThat(response).isNotNull();
    }

    @Test
    @DisplayName("Сохранение порядка")
    void actionsOrder() {
        Action firstAction = new AbstractAction() {
            @Override
            protected Optional<ResponseData> process(Update update) {
                return Optional.of(new ResponseData(new SetWebhook(), new SetMyCommands()));
            }
        };

        Action secondAction = new AbstractAction() {
            @Override
            protected Optional<ResponseData> process(Update update) {
                return Optional.of(new ResponseData(new SendMessage(1L, "test"), new SetMyCommands()));
            }
        };

        ActionFacade actionFacade = new ActionFacade(new Chain(firstAction, secondAction).getChain());

        var response = actionFacade.apply(new Update());

        assertThat(response.request()).isInstanceOf(SetWebhook.class);
    }
}
