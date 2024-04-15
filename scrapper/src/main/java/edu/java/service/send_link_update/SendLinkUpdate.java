package edu.java.service.send_link_update;

import edu.java.client.bot.dto.request.SendUpdatesRequest;


public interface SendLinkUpdate {
    void send(SendUpdatesRequest request);
}
