package edu.java.util;

import edu.java.client.github.GitHubClient;
import edu.java.client.github.dto.request.ListRepositoryEventsRequest;
import edu.java.client.github.dto.response.RepositoryEvent;
import edu.java.configuration.properties.GitHubConfig;
import java.net.URI;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GithubUpdatesExtractor implements UpdatesExtractor {
    private final GitHubConfig config;

    private final GitHubClient client;

    private static final String TEMPLATE = """
            Событие для репозитория [%s](%s)

            Тип события: %s
            Автор: [%s](%s)
            Дата и время: %s
            """;

    @Override
    public List<Update> getUpdates(URI url, OffsetDateTime timestamp) {
        String regex = "^(?:https?://)?(?:www\\.)?github\\.com/([^/]+)/([^/]+)(?:\\.git)?$";
        Matcher matcher = Pattern.compile(regex).matcher(url.toString());

        if (!matcher.find()) {
            return List.of();
        }

        String owner = matcher.group(1);
        String repo = matcher.group(2);
        int page = 1;

        List<Update> updates = new ArrayList<>();

        while (true) {
            ListRepositoryEventsRequest request = new ListRepositoryEventsRequest();
            request.setPerPage(config.perPage());
            request.setPage(page);
            List<RepositoryEvent> events = client.listRepositoryEvents(owner, repo, request);

            if (events.isEmpty()) {
                break;
            }

            for (RepositoryEvent event : events) {
                if (event.createdAt().isBefore(timestamp)) {
                    break;
                }

                updates.add(new Update(url, String.format(
                        TEMPLATE,
                        event.repo().name(),
                        event.repo().url(),
                        event.type(),
                        event.actor().displayLogin(),
                        event.actor().url(),
                        event.createdAt()
                )));
            }

            page++;
        }

        return updates;
    }
}
