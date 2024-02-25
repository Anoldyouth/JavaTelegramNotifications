package edu.java.bot.action;

import com.pengrad.telegrambot.model.CallbackQuery;
import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.User;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ActionHelper {
    public static Update makeMockUpdateMessage(String messageText) {
        var chatMock = mock(Chat.class);
        when(chatMock.id()).thenReturn(1L);

        var messageMock = mock(Message.class);
        when(messageMock.text()).thenReturn(messageText);
        when(messageMock.chat()).thenReturn(chatMock);

        var updateMock = mock(Update.class);
        when(updateMock.message()).thenReturn(messageMock);

        return updateMock;
    }

    public static Update makeMockUpdateCallBackQuery(String data) {
        var userMock = mock(User.class);
        when(userMock.id()).thenReturn(1L);

        var callbackQueryMock = mock(CallbackQuery.class);
        when(callbackQueryMock.data()).thenReturn(data);
        when(callbackQueryMock.from()).thenReturn(userMock);

        var updateMock = mock(Update.class);
        when(updateMock.callbackQuery()).thenReturn(callbackQueryMock);

        return updateMock;
    }

    public static Update makeMockUpdateEmpty() {
        return mock(Update.class);
    }
}
