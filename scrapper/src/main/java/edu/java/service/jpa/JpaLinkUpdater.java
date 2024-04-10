package edu.java.service.jpa;

import edu.java.client.bot.BotClient;
import edu.java.client.bot.dto.request.SendUpdatesRequest;
import edu.java.dao.jpa.JpaLinkRepository;
import edu.java.dao.jpa.entity.LinkEntity;
import edu.java.dao.jpa.entity.TgChatEntity;
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
import org.springframework.data.domain.PageRequest;

@RequiredArgsConstructor
public class JpaLinkUpdater implements LinkUpdater {
    private static final Logger LOGGER = LogManager.getLogger();

    private final JpaLinkRepository linkRepository;

    private final BotClient botClient;

    private final GithubUpdatesExtractor githubUpdatesExtractor;

    private final StackOverflowUpdatesExtractor stackOverflowUpdatesExtractor;

    @Override
    public int update() {
        OffsetDateTime timestamp = OffsetDateTime.now();
        int page = 0;
        int count = 0;

        while (true) {
            List<LinkEntity> links = linkRepository.findByCreatedAtLessThan(timestamp, PageRequest.of(page, LIMIT));

            if (links == null || links.isEmpty()) {
                break;
            }

            for (LinkEntity link : links) {
                try {
                    linkUpdates(link, timestamp);
                } catch (Exception exception) {
                    LOGGER.error(exception);
                    count--;
                }
            }

            count += links.size();
            page++;
        }

        return count;
    }

    private void linkUpdates(LinkEntity link, OffsetDateTime timestamp) {
        List<UpdatesExtractor.Update> updates = getUpdatesExtractor(link.getUrl())
                .getUpdates(link.getUrl(), link.getLastCheckAt());

        List<Long> tgChatIds = link.getChats().stream().map(TgChatEntity::getId).toList();

        for (UpdatesExtractor.Update update : updates) {
            SendUpdatesRequest request = new SendUpdatesRequest(update.url(), update.message(), tgChatIds);
            botClient.updates(request);
        }

        link.setCreatedAt(timestamp);
        linkRepository.saveAndFlush(link);
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
