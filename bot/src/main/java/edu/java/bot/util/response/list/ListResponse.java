package edu.java.bot.util.response.list;

import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import edu.java.bot.client.scrapper.dto.response.link.LinkResponse;
import edu.java.bot.util.response.ResponseData;
import java.util.List;

public class ListResponse extends ListScenarioAbstractResponse {
    private final List<LinkResponse> links;
    private final boolean deletePreviousMessage;
    private final Long previousOffset;
    private final Long nextOffset;

    public ListResponse(
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

    public ListResponse(
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
        StringBuilder message = new StringBuilder("*Список отслеживаемых страниц:*\n\n");

        var keyboard = new InlineKeyboardMarkup();
        for (LinkResponse link : this.links) {
            message.append(link.url().toString()).append("\n");
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
                createMessageRequest(message.toString()).replyMarkup(keyboard),
                getCommands(),
                deletePreviousMessage
        );
    }
}
