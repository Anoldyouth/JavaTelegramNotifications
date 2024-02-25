package edu.java.bot.action;

import edu.java.bot.util.action.Action;
import edu.java.bot.util.action.UntrackUrlAction;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Assertions;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UntrackUrlActionUnitTest {
    Action action;

    @BeforeEach
    void prepare() {
        action = new UntrackUrlAction();
    }

    @Test
    void correctQuery() {
        var update = ActionHelper.makeMockUpdateCallBackQuery("https://www.google.com");

        var response = action.apply(update);

        assertThat(response).isNotNull();
        Assertions.assertTrue(((String) response.request().getParameters().get("text"))
                .startsWith("Ссылка https://www.google.com успешно удалена из отслеживания."));
    }

    @Test
    void anotherUpdateContent() {
        var update = ActionHelper.makeMockUpdateEmpty();

        assertThrows(NullPointerException.class, () -> action.apply(update));
    }
}
