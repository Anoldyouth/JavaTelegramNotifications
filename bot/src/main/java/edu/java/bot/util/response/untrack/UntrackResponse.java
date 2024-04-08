package edu.java.bot.util.response.untrack;

import com.pengrad.telegrambot.model.BotCommand;
import com.pengrad.telegrambot.model.botcommandscope.BotCommandsScopeChat;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.request.SetMyCommands;
import edu.java.bot.client.scrapper.dto.response.link.LinkResponse;
import edu.java.bot.util.CommandEnum;
import edu.java.bot.util.response.ResponseData;
import java.util.ArrayList;
import java.util.List;

public class UntrackResponse extends UntrackUrlScenarioAbstractResponse {
    private final List<LinkResponse> links;
    private final boolean deletePreviousMessage;
    private final Long previousOffset;
    private final Long nextOffset;

    public UntrackResponse(
            long chatId,
            List<LinkResponse> links,
            boolean deletePreviousMessage,
            Long previousOffset,
            Long nextOffset
    ) {
        super(chatId);

        this.links = links;
        this.deletePreviousMessage = deletePreviousMessage;
        this.previousOffset = previousOffset;
        this.nextOffset = nextOffset;
    }

    public UntrackResponse(
            long chatId,
            List<LinkResponse> links,
            boolean deletePreviousMessage
    ) {
        super(chatId);

        this.links = links;
        this.deletePreviousMessage = deletePreviousMessage;
        this.previousOffset = null;
        this.nextOffset = null;
    }

    @Override
    @SuppressWarnings("MultipleStringLiterals")
    public ResponseData makeResponse() {
        String message = "Выберите, для какой ссылки прекратить отслеживание.\n"
                + "Для отмены выбора используйте " + CommandEnum.CANCEL.getCommand() + ".";

        var keyboard = new InlineKeyboardMarkup();

        for (LinkResponse link : this.links) {
            keyboard.addRow(new InlineKeyboardButton(link.url().toString()).callbackData(String.valueOf(link.id())));
        }

        if (previousOffset != null) {
            keyboard.addRow(
                    new InlineKeyboardButton("Показать предыдущие").callbackData("offset_" + previousOffset)
            );
        }

        if (nextOffset != null) {
            keyboard.addRow(
                    new InlineKeyboardButton("Показать еще").callbackData("offset_" + nextOffset)
            );
        }

        return new ResponseData(
                createMessageRequest(message).replyMarkup(keyboard),
                getCommands(),
                deletePreviousMessage
        );
    }

    @Override
    public SetMyCommands getCommands() {
        List<BotCommand> commands = new ArrayList<>();
        commands.add(new BotCommand(CommandEnum.CANCEL.getCommand(), CommandEnum.CANCEL.getDescription()));

        return new SetMyCommands(commands.toArray(new BotCommand[]{})).scope(new BotCommandsScopeChat(chatId));
    }
}
