package edu.java.service.jdbc;

import edu.java.client.bot.dto.request.SendUpdatesRequest;
import edu.java.dao.jdbc.JdbcLinkDao;
import edu.java.dao.jdbc.JdbcTgChatLinkDao;
import edu.java.model.Link;
import edu.java.service.LinkUpdater;
import edu.java.service.send_link_update.SendLinkUpdate;
import edu.java.util.GithubUpdatesExtractor;
import edu.java.util.StackOverflowUpdatesExtractor;
import edu.java.util.UpdatesExtractor;
import java.net.URI;
import java.time.OffsetDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@RequiredArgsConstructor
public class JdbcLinkUpdater implements LinkUpdater {
    private static final Logger LOGGER = LogManager.getLogger();
    private final JdbcLinkDao linkDao;

    private final JdbcTgChatLinkDao tgChatLinkDao;

    private final SendLinkUpdate sendLinkUpdate;

    private final GithubUpdatesExtractor githubUpdatesExtractor;

    private final StackOverflowUpdatesExtractor stackOverflowUpdatesExtractor;

    @Override
    public int update() {
        OffsetDateTime timestamp = OffsetDateTime.now();
        int offset = 0;
        int count = 0;

        while (true) {
            List<Link> links = linkDao.getAllWhereLastCheckAtBefore(timestamp, offset, LIMIT);

            if (links == null || links.isEmpty()) {
                break;
            }

            for (Link link : links) {
                linkUpdates(link, timestamp);
            }

            count += links.size();
            offset += links.size();
        }

        return count;
    }

    private void linkUpdates(Link link, OffsetDateTime timestamp) {
        List<UpdatesExtractor.Update> updates = getUpdatesExtractor(link.url())
                .getUpdates(link.url(), link.lastCheckAt());

        List<Long> tgChatIds = tgChatLinkDao.getTgChatIdsByLinkId(link.id());

        for (UpdatesExtractor.Update update : updates) {
            SendUpdatesRequest request = new SendUpdatesRequest(update.url(), update.message(), tgChatIds);
            sendLinkUpdate.send(request);
        }

        linkDao.update(link.id(), timestamp);
    }

    private UpdatesExtractor getUpdatesExtractor(URI url) {
        if (url.toString().startsWith("https://github.com")) {
            return githubUpdatesExtractor;
        }

        if (url.toString().startsWith("https://stackoverflow.com")) {
            return stackOverflowUpdatesExtractor;
        }

        throw new RuntimeException("Unsupported link: " + url);
    }
}
