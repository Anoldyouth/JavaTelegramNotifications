package edu.java.service.send_link_update;

import edu.java.client.bot.BotClient;
import edu.java.client.bot.dto.request.SendUpdatesRequest;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ClientSendLinkUpdate implements SendLinkUpdate {
    private final BotClient botClient;

    @Override
    public void send(SendUpdatesRequest request) {
        botClient.updates(request);
    }
}
