package edu.java.bot.util.response.main;

import edu.java.bot.util.CommandEnum;
import edu.java.bot.util.response.ResponseData;

public class HelpResponse extends MainScenarioAbstractResponse {

    public HelpResponse(long chatId) {
        super(chatId);
    }

    @Override
    @SuppressWarnings("MultipleStringLiterals")
    public ResponseData makeResponse() {
        String message = "*Список команд:*\n\n"
                + CommandEnum.TRACK.getCommand() + " - " + CommandEnum.TRACK.getDescription() + "\n"
                + CommandEnum.UNTRACK.getCommand() + " - " + CommandEnum.UNTRACK.getDescription() + "\n"
                + CommandEnum.LIST.getCommand() + " - " + CommandEnum.LIST.getDescription() + "\n";

        return new ResponseData(createMessageRequest(message), getCommands(), false);
    }
}
