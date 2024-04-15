package edu.java.bot.util.response.main;

import com.pengrad.telegrambot.model.BotCommand;
import com.pengrad.telegrambot.model.botcommandscope.BotCommandsScopeChat;
import com.pengrad.telegrambot.request.SetMyCommands;
import edu.java.bot.util.CommandEnum;
import edu.java.bot.util.response.AbstractResponse;
import java.util.ArrayList;
import java.util.List;

public abstract class MainScenarioAbstractResponse extends AbstractResponse {
    public MainScenarioAbstractResponse(long chatId) {
        super(chatId);
    }

    @Override
    public SetMyCommands getCommands() {
        List<BotCommand> commands = new ArrayList<>();
        commands.add(new BotCommand(CommandEnum.START.getCommand(), CommandEnum.START.getDescription()));
        commands.add(new BotCommand(CommandEnum.HELP.getCommand(), CommandEnum.HELP.getDescription()));
        commands.add(new BotCommand(CommandEnum.TRACK.getCommand(), CommandEnum.TRACK.getDescription()));
        commands.add(new BotCommand(CommandEnum.UNTRACK.getCommand(), CommandEnum.UNTRACK.getDescription()));
        commands.add(new BotCommand(CommandEnum.LIST.getCommand(), CommandEnum.LIST.getDescription()));

        return new SetMyCommands(commands.toArray(new BotCommand[]{})).scope(new BotCommandsScopeChat(chatId));
    }
}
