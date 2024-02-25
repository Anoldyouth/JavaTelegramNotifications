package edu.java.bot.action;

import edu.java.bot.util.CommandEnum;
import edu.java.bot.util.action.Action;
import edu.java.bot.util.action.CancelAction;
import edu.java.bot.util.action.StartAction;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Assertions;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class StartActionUnitTest {
    Action action;

    @BeforeEach
    void prepare() {
        action = new StartAction();
    }

    @Test
    void correctCommand() {
        var update = ActionHelper.makeMockUpdateMessage(CommandEnum.START.getCommand());

        var response = action.apply(update);

        assertThat(response).isNotNull();
        Assertions.assertTrue(((String) response.request().getParameters().get("text")).startsWith("Привет! Этот Бот"));
    }

    @Test
    void anotherMessage() {
        var update = ActionHelper.makeMockUpdateMessage("test");

        var response = action.apply(update);

        assertThat(response).isNotNull();
        Assertions.assertTrue(((String) response.request().getParameters().get("text"))
                .startsWith("Неизвестная команда"));
    }

    @Test
    void anotherUpdateContent() {
        var update = ActionHelper.makeMockUpdateEmpty();

        var response = action.apply(update);

        assertThat(response).isNotNull();
        Assertions.assertTrue(((String) response.request().getParameters().get("text"))
                .startsWith("Неизвестная команда"));
    }
}
