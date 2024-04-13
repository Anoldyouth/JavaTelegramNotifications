package edu.java.scrapper;

import edu.java.client.bot.BotClient;
import edu.java.client.bot.dto.request.SendUpdatesRequest;
import edu.java.dao.jdbc.JdbcLinkDao;
import edu.java.dao.jdbc.JdbcTgChatLinkDao;
import edu.java.dao.jpa.JpaLinkRepository;
import edu.java.service.LinkUpdater;
import edu.java.service.jdbc.JdbcLinkUpdater;
import edu.java.service.jpa.JpaLinkUpdater;
import edu.java.util.GithubUpdatesExtractor;
import edu.java.util.StackOverflowUpdatesExtractor;
import edu.java.util.UpdatesExtractor;
import java.net.URI;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.Captor;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
public class LinkUpdaterIntegrationTest extends IntegrationTest {
    @Autowired
    private JdbcLinkDao linkDao;
    @Autowired
    private JdbcTgChatLinkDao tgChatLinkDao;

    @Autowired
    private JpaLinkRepository linkRepository;

    BotClient botClient;
    GithubUpdatesExtractor githubUpdatesExtractor;
    StackOverflowUpdatesExtractor stackOverflowUpdatesExtractor;

    @Captor
    ArgumentCaptor<SendUpdatesRequest> captor;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void prepare() {
        botClient = mock(BotClient.class);
        githubUpdatesExtractor = mock(GithubUpdatesExtractor.class);
        stackOverflowUpdatesExtractor = mock(StackOverflowUpdatesExtractor.class);
    }

    @Test
    @Transactional
    @Rollback
    void updateJdbc() {
        LinkUpdater service = new JdbcLinkUpdater(linkDao, tgChatLinkDao, botClient, githubUpdatesExtractor, stackOverflowUpdatesExtractor);

        jdbcTemplate.execute("""
                INSERT INTO links (id, url, last_check_at, created_at)
                VALUES (
                    1,
                    'https://github.com/Anoldyouth/Java-Telegram-Notifications',
                    '2000-03-16 00:37:57.491000 +00:00',
                    '2000-03-16 00:37:57.491000 +00:00'
                ), (
                    2,
                    'https://stackoverflow.com/questions/1/test',
                    '2000-03-16 00:37:57.491000 +00:00',
                    '2000-03-16 00:37:57.491000 +00:00'
                );
                
                INSERT INTO tg_chats (id, state, created_at)
                VALUES (
                    1,
                    'MAIN',
                    '2024-03-16 00:37:57.491000 +00:00'
                ), (
                    2,
                    'MAIN',
                    '2024-03-16 00:37:57.491000 +00:00'
                );
                
                INSERT INTO tg_chats_links (tg_chat_id, link_id)
                VALUES (1, 1), (1, 2), (2, 2);
                """);

        URI githubUrl = URI.create("https://github.com/Anoldyouth/Java-Telegram-Notifications");
        URI stackUrl = URI.create("https://stackoverflow.com/questions/1/test");

        when(githubUpdatesExtractor.getUpdates(any(), any()))
                .thenReturn(List.of(new UpdatesExtractor.Update(githubUrl, "github")));

        when(stackOverflowUpdatesExtractor.getUpdates(any(), any()))
                .thenReturn(List.of(new UpdatesExtractor.Update(stackUrl, "stack")));

        service.update();

        verify(botClient, times(2)).updates(captor.capture());
        List<SendUpdatesRequest> requests = captor.getAllValues();

        assertThat(requests.get(0)).isEqualTo(new SendUpdatesRequest(githubUrl, "github", List.of(1L)));
        assertThat(requests.get(1)).isEqualTo(new SendUpdatesRequest(stackUrl, "stack", List.of(1L, 2L)));
    }

    @Test
    @Transactional
    @Rollback
    void updateJpa() {
        LinkUpdater service = new JpaLinkUpdater(linkRepository, botClient, githubUpdatesExtractor, stackOverflowUpdatesExtractor);

        jdbcTemplate.execute("""
                INSERT INTO links (id, url, last_check_at, created_at)
                VALUES (
                    1,
                    'https://github.com/Anoldyouth/Java-Telegram-Notifications',
                    '2000-03-16 00:37:57.491000 +00:00',
                    '2000-03-16 00:37:57.491000 +00:00'
                ), (
                    2,
                    'https://stackoverflow.com/questions/1/test',
                    '2000-03-16 00:37:57.491000 +00:00',
                    '2000-03-16 00:37:57.491000 +00:00'
                );
                
                INSERT INTO tg_chats (id, state, created_at)
                VALUES (
                    1,
                    'MAIN',
                    '2024-03-16 00:37:57.491000 +00:00'
                ), (
                    2,
                    'MAIN',
                    '2024-03-16 00:37:57.491000 +00:00'
                );
                
                INSERT INTO tg_chats_links (tg_chat_id, link_id)
                VALUES (1, 1), (1, 2), (2, 2);
                """);

        URI githubUrl = URI.create("https://github.com/Anoldyouth/Java-Telegram-Notifications");
        URI stackUrl = URI.create("https://stackoverflow.com/questions/1/test");

        when(githubUpdatesExtractor.getUpdates(any(), any()))
                .thenReturn(List.of(new UpdatesExtractor.Update(githubUrl, "github")));

        when(stackOverflowUpdatesExtractor.getUpdates(any(), any()))
                .thenReturn(List.of(new UpdatesExtractor.Update(stackUrl, "stack")));

        service.update();

        verify(botClient, times(2)).updates(captor.capture());
        List<SendUpdatesRequest> requests = captor.getAllValues();

        assertThat(requests.get(0)).isEqualTo(new SendUpdatesRequest(githubUrl, "github", List.of(1L)));

        SendUpdatesRequest stackRequest = requests.get(1);
        assertThat(stackRequest.url()).isEqualTo(stackUrl);
        assertThat(stackRequest.description()).isEqualTo("stack");
        assertThat(stackRequest.tgChatIds()).contains(1L, 2L);
    }
}
