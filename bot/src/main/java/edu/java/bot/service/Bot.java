package edu.java.bot.service;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.DeleteMessage;
import edu.java.bot.client.scrapper.ScrapperClient;
import edu.java.bot.util.ScenarioDispatcher;
import edu.java.bot.util.action.Action;
import edu.java.bot.util.action.ActionFacade;
import jakarta.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class Bot implements AutoCloseable, UpdatesListener {
    private static final Logger LOGGER = LogManager.getLogger();

    private final TelegramBot bot;

    private final ScenarioDispatcher mapper;

    private final ActionFacade facade;

    private final ScrapperClient scrapperClient;

    @Override
    public int process(List<Update> list) {
        for (Update update: list) {
            ChatInfo chatInfo = getChatInfo(update);
            List<Action> scenario = mapper.getScenario(chatInfo.state);

            try {
                var response = facade.applyScenario(
                        scenario,
                        update,
                        chatInfo.id
                );

                // Удаление сообщения
                if (response.deleteLastMessage()) {
                    deleteMessage(chatInfo.id, update);
                }
                // Отправка сообщения
                bot.execute(response.request());
                if (response.menu() != null) {
                    // Отправка меню команд
                    bot.execute(response.menu());
                }
            } catch (Throwable throwable) {
                LOGGER.error(throwable.toString());
                LOGGER.error(Arrays.toString(throwable.getStackTrace()));
            }
        }

        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

    @Override
    public void close() {
        bot.shutdown();
    }

    @PostConstruct
    public void start() {
        bot.setUpdatesListener(this);

        LOGGER.debug("Bot started");
    }

    private ChatInfo getChatInfo(Update update) {
        long chatId = 0;

        if (update.message() != null) {
            chatId = update.message().chat().id();
        }

        if (update.editedMessage() != null) {
            chatId = update.editedMessage().chat().id();
        }

        if (update.inlineQuery() != null) {
            chatId = update.inlineQuery().from().id();
        }

        if (update.chosenInlineResult() != null) {
            chatId = update.chosenInlineResult().from().id();
        }

        if (update.callbackQuery() != null) {
            chatId = update.callbackQuery().from().id();
        }

        try {
            var response = scrapperClient.getTgChatState(chatId);
            return new ChatInfo(chatId, response.state());
        } catch (Exception exception) {
            return new ChatInfo(chatId, ScenarioDispatcher.ScenarioType.START);
        }
    }

    private void deleteMessage(long chatId, Update update) {
        try {
            int messageId = update.callbackQuery().message().messageId(); // Нет аналога deprecated поля
            var message = new DeleteMessage(chatId, messageId);

            bot.execute(message);
        } catch (Exception ignored) {
            // Сообщение нельзя удалить после 48 часов
        }
    }

    private record ChatInfo(long id, ScenarioDispatcher.ScenarioType state) {
    }
}
