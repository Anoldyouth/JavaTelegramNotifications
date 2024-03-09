package edu.java.bot.action;

import edu.java.bot.util.action.Action;
import edu.java.bot.util.action.TrackUrlAction;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TrackUrlActionUnitTest {
    Action action;

    @BeforeEach
    void prepare() {
        action = new TrackUrlAction();
    }

    @Test
    void correctUrl() {
        var update = ActionHelper.makeMockUpdateMessage("https://www.google.com");

        var response = action.apply(update);

        assertThat(response).isNotNull();
        Assertions.assertTrue(((String) response.request().getParameters().get("text"))
                .startsWith("Ссылка https://www.google.com успешно добавлена для отслеживания."));
    }

    @Test
    void incorrectUrl() {
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
