package edu.java.bot;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.BaseRequest;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.request.SetMyCommands;
import edu.java.bot.util.response.ResponseData;
import edu.java.bot.service.Bot;
import edu.java.bot.util.Chain;
import edu.java.bot.util.ChainMapper;
import edu.java.bot.util.action.AbstractAction;
import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
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
    TelegramBot telegramBotMock;
    ChainMapper chainMapperMock;

    @Captor
    ArgumentCaptor<BaseRequest<?, ?>> captor;

    @BeforeEach
    void startBot() {
        var telegramBot = mock(TelegramBot.class);
        var chainMapper = mock(ChainMapper.class);

        Chain chain = new Chain(new AbstractAction() {
            @Override
            protected Optional<ResponseData> process(Update update) {
                return Optional.of(new ResponseData(new SendMessage(1L, "test"), new SetMyCommands()));
            }
        });

        when(chainMapper.getChain(Mockito.any(ChainMapper.ChainType.class))).thenReturn(chain);

        telegramBotMock = telegramBot;
        chainMapperMock = chainMapper;

        bot = new Bot(telegramBot, chainMapper);
        bot.start();
    }

    @Test
    void responseSent() {
        List<Update> updates = List.of(mock(Update.class));

        int result = bot.process(updates);
        verify(telegramBotMock, times(2)).execute(captor.capture());
        List<BaseRequest<?, ?>> capturedArguments = captor.getAllValues();

        assertThat(capturedArguments.get(0)).isInstanceOf(SendMessage.class);
        assertThat(capturedArguments.get(1)).isInstanceOf(SetMyCommands.class);
        assertThat(result).isEqualTo(UpdatesListener.CONFIRMED_UPDATES_ALL);
    }
}
