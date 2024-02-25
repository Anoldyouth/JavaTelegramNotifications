package edu.java.bot.service;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
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

    @Override
    public int process(List<Update> list) {
        /*
         * TODO
         * Придумать, как защититься от отказов системы
         * Например, на одном сообщении все сломалось - что делать дальше
         */
        for (Update update: list) {
            LOGGER.debug("Bot get update");
            // TODO Добавить маппинг цепочки в зависимости от текущего состояния приложения для пользователя
            List<Action> scenario = mapper.getScenario(ScenarioDispatcher.ScenarioType.MAIN);

            try {
                var response = facade.applyScenario(scenario, update);

                // Отправка сообщения
                bot.execute(response.request());
                // Отправка меню команд
                bot.execute(response.menu());
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
}
