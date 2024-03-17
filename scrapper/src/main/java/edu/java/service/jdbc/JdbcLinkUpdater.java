package edu.java.service.jdbc;

import edu.java.client.bot.BotClient;
import edu.java.client.bot.dto.request.SendUpdatesRequest;
import edu.java.dao.JdbcLinkDao;
import edu.java.dao.JdbcTgChatLinkDao;
import edu.java.model.Link;
import edu.java.service.LinkUpdater;
import edu.java.util.GithubUpdatesExtractor;
import edu.java.util.StackOverflowUpdatesExtractor;
import edu.java.util.UpdatesExtractor;
import java.net.URI;
import java.time.OffsetDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JdbcLinkUpdater implements LinkUpdater {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final int LIMIT = 50;

    private final JdbcLinkDao linkDao;

    private final JdbcTgChatLinkDao tgChatLinkDao;

    private final BotClient botClient;

    @Override
    public int update() {
        OffsetDateTime timestamp = OffsetDateTime.now();
        int count = 0;

        while (true) {
            List<Link> links = linkDao.getAllWhereLastCheckAtBefore(timestamp, count, LIMIT);

            if (links == null) {
                break;
            }

            for (Link link : links) {
                try {
                    linkUpdates(link, timestamp);
                } catch (Exception exception) {
                    LOGGER.error(exception);
                    count--;
                }
            }

            count += links.size();
        }

        return count;
    }

    private void linkUpdates(Link link, OffsetDateTime timestamp) {
        List<UpdatesExtractor.Update> updates = getUpdatesExtractor(link.url())
                .getUpdates(link.url(), link.lastCheckAt());

        List<Long> tgChatIds = tgChatLinkDao.getTgChatIdsByLinkId(link.id());

        for (UpdatesExtractor.Update update : updates) {
            SendUpdatesRequest request = new SendUpdatesRequest(update.url(), update.message(), tgChatIds);
            botClient.updates(request);
        }

        linkDao.update(link.id(), timestamp);
    }

    private UpdatesExtractor getUpdatesExtractor(URI url) {
        if (url.toString().startsWith("https://github.com")) {
            return new GithubUpdatesExtractor();
        }

        if (url.toString().startsWith("https://stackoverflow.com")) {
            return new StackOverflowUpdatesExtractor();
        }

        throw new RuntimeException("Unsupported link: " + url);
    }
}
