package edu.java.bot;

import com.pengrad.telegrambot.model.CallbackQuery;
import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.User;
import edu.java.bot.util.CommandEnum;
import edu.java.bot.util.action.Action;
import edu.java.bot.util.action.CancelAction;
import edu.java.bot.util.action.HelpAction;
import edu.java.bot.util.action.ListAction;
import edu.java.bot.util.action.StartAction;
import edu.java.bot.util.action.TrackAction;
import edu.java.bot.util.action.TrackUrlAction;
import edu.java.bot.util.action.UnknownCommandAction;
import edu.java.bot.util.action.UntrackAction;
import edu.java.bot.util.action.UntrackUrlAction;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Assertions;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ActionsUnitTest {
    static Arguments[] commands() {
        return new Arguments[]{
                Arguments.of(
                        makeMockUpdateMessage(CommandEnum.START.getCommand()),
                        new StartAction(),
                        "Привет! Этот Бот"
                ),
                Arguments.of(
                        makeMockUpdateMessage(CommandEnum.CANCEL.getCommand()),
                        new CancelAction(),
                        "Привет! Этот Бот"
                ),
                Arguments.of(
                        makeMockUpdateMessage(CommandEnum.HELP.getCommand()),
                        new HelpAction(),
                        "*Список команд:*"
                ),
                Arguments.of(
                        makeMockUpdateMessage(CommandEnum.TRACK.getCommand()),
                        new TrackAction(),
                        "Введите ссылку"
                ),
                Arguments.of(
                        makeMockUpdateMessage(CommandEnum.UNTRACK.getCommand()),
                        new UntrackAction(),
                        "Выберите, для какой ссылки прекратить отслеживание"
                ),
                Arguments.of(
                        makeMockUpdateMessage(CommandEnum.LIST.getCommand()),
                        new ListAction(),
                        "Список отслеживаемых страниц"
                ),
                Arguments.of(
                        makeMockUpdateMessage("https://www.google.com"),
                        new TrackUrlAction(),
                        "Ссылка https://www.google.com успешно добавлена для отслеживания."
                ),
                Arguments.of(
                        makeMockUpdateCallBackQuery("https://www.google.com"),
                        new UntrackUrlAction(),
                        "Ссылка https://www.google.com успешно удалена из отслеживания."
                ),
                Arguments.of(
                        makeMockUpdateMessage("test"),
                        new UnknownCommandAction(),
                        "Неизвестная команда"
                )
        };
    }

    @ParameterizedTest
    @DisplayName("Соответствие действия команде")
    @MethodSource("commands")
    void ActionCommandTest(Update update, Action action, String message) {
        var response = action.apply(update);

        assertThat(response).isNotNull();
        Assertions.assertTrue(((String) response.request().getParameters().get("text")).startsWith(message));
    }

    static Arguments[] actionsWithNull() {
        return new Arguments[]{
                Arguments.of(new StartAction()),
                Arguments.of(new CancelAction()),
                Arguments.of(new HelpAction()),
                Arguments.of(new TrackAction()),
                Arguments.of(new UntrackAction()),
                Arguments.of(new ListAction()),
                Arguments.of(new TrackUrlAction())
        };
    }

    @ParameterizedTest
    @DisplayName("Пришло неожидаемое сообщение")
    @MethodSource("actionsWithNull")
    void anotherMessage(Action action) {
        var update = makeMockUpdateMessage("test");

        assertThrows(NullPointerException.class, () -> action.apply(update));
    }

    private static Update makeMockUpdateMessage(String messageText) {
        var chatMock = mock(Chat.class);
        when(chatMock.id()).thenReturn(1L);

        var messageMock = mock(Message.class);
        when(messageMock.text()).thenReturn(messageText);
        when(messageMock.chat()).thenReturn(chatMock);

        var updateMock = mock(Update.class);
        when(updateMock.message()).thenReturn(messageMock);

        return updateMock;
    }

    private static Update makeMockUpdateCallBackQuery(String data) {
        var userMock = mock(User.class);
        when(userMock.id()).thenReturn(1L);

        var callbackQueryMock = mock(CallbackQuery.class);
        when(callbackQueryMock.data()).thenReturn(data);
        when(callbackQueryMock.from()).thenReturn(userMock);

        var updateMock = mock(Update.class);
        when(updateMock.callbackQuery()).thenReturn(callbackQueryMock);

        return updateMock;
    }
}
