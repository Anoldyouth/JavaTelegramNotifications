package edu.java.bot.util.response.track;

import com.pengrad.telegrambot.model.BotCommand;
import com.pengrad.telegrambot.model.botcommandscope.BotCommandsScopeChat;
import com.pengrad.telegrambot.request.SetMyCommands;
import edu.java.bot.util.CommandEnum;
import edu.java.bot.util.response.AbstractResponse;
import java.util.ArrayList;
import java.util.List;

public abstract class TrackUrlScenarioAbstractResponse extends AbstractResponse {
    public TrackUrlScenarioAbstractResponse(long chatId) {
        super(chatId);
    }

    @Override
    public SetMyCommands getCommands() {
        List<BotCommand> commands = new ArrayList<>();
        commands.add(new BotCommand(CommandEnum.CANCEL.getCommand(), CommandEnum.CANCEL.getDescription()));

        return new SetMyCommands(commands.toArray(new BotCommand[]{})).scope(new BotCommandsScopeChat(chatId));
    }
}
