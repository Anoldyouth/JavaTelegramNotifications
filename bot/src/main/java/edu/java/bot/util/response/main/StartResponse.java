package edu.java.bot.util.response.main;

import edu.java.bot.util.CommandEnum;
import edu.java.bot.util.response.ResponseData;

public class StartResponse extends MainScenarioAbstractResponse {
    public StartResponse(long chatId) {
        super(chatId);
    }

    @Override
    public ResponseData makeResponse() {
        String message = "Привет! Этот Бот создан для удобного получения оповещений об изменении"
                + " на различных веб-ресурсах: GitHub, StackOverFlow, Reddit, HackerNews, YouTube и т.п.\n\n"
                + "На данный момент поддерживается только GitHub и StackOverFlow.\n\n"
                + CommandEnum.HELP.getCommand() + " - вывести список команд";

        return new ResponseData(createMessageRequest(message), getCommands(), false);
    }
}
