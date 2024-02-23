package edu.java.bot.service;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import edu.java.bot.util.Chain;
import edu.java.bot.util.ChainMapper;
import edu.java.bot.util.action.ActionFacade;
import jakarta.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class Bot implements AutoCloseable, UpdatesListener {
    private static final Logger LOGGER = LogManager.getLogger();
    private final TelegramBot bot;

    private final ChainMapper mapper;

    public Bot(TelegramBot telegramBot, ChainMapper chainMapper) {
        this.bot = telegramBot;
        this.mapper = chainMapper;
    }

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
            Chain commandsChain = mapper.getChain(ChainMapper.ChainType.MAIN);
            ActionFacade facade = new ActionFacade(commandsChain.getChain());

            try {
                var response = facade.apply(update);
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
    public void close() throws Exception {
        bot.shutdown();
    }

    @PostConstruct
    public void start() {
        bot.setUpdatesListener(this);

        LOGGER.debug("Bot started");
    }
}
