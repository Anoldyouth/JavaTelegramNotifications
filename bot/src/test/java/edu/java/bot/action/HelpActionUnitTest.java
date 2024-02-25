package edu.java.bot.action;

import edu.java.bot.util.CommandEnum;
import edu.java.bot.util.action.Action;
import edu.java.bot.util.action.HelpAction;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Assertions;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class HelpActionUnitTest {
    Action action;

    @BeforeEach
    void prepare() {
        action = new HelpAction();
    }

    @Test
    void correctCommand() {
        var update = ActionHelper.makeMockUpdateMessage(CommandEnum.HELP.getCommand());

        var response = action.apply(update);

        assertThat(response).isNotNull();
        Assertions.assertTrue(((String) response.request().getParameters().get("text")).startsWith("*Список команд:*"));
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
