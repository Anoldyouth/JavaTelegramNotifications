package edu.java.bot.action;

import edu.java.bot.util.action.Action;
import edu.java.bot.util.action.UnknownCommandAction;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UnknownActionUnitTest {
    Action action;

    @BeforeEach
    void prepare() {
        action = new UnknownCommandAction();
    }

    @Test
    void anyMessage() {
        var update = ActionHelper.makeMockUpdateEmpty();

        var response = action.apply(update, 1);

        assertThat(response).isNotNull();
        Assertions.assertTrue(((String) response.request().getParameters().get("text"))
                .startsWith("Неизвестная команда"));
    }
}
