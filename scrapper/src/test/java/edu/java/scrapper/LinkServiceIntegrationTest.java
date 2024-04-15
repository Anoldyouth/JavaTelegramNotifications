package edu.java.scrapper;

import edu.java.dao.jdbc.JdbcLinkDao;
import edu.java.dao.jdbc.JdbcTgChatLinkDao;
import edu.java.dao.jpa.JpaLinkRepository;
import edu.java.dao.jpa.JpaTgChatRepository;
import edu.java.exception.NotFoundException;
import edu.java.service.LinkService;
import edu.java.service.jdbc.JdbcLinkService;
import edu.java.service.jpa.JpaLinkService;
import java.net.URI;
import java.sql.ResultSet;
import java.util.stream.Stream;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class LinkServiceIntegrationTest extends IntegrationTest {
    @Autowired
    private JdbcLinkDao linkDao;
    @Autowired
    private JdbcTgChatLinkDao tgChatLinkDao;

    @Autowired
    private JpaLinkRepository linkRepository;
    @Autowired
    private JpaTgChatRepository tgChatRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @ParameterizedTest
    @MethodSource
    @Transactional
    @Rollback
    void addNew(LinkService service) {
        jdbcTemplate.execute("""
                INSERT INTO tg_chats (id, state, created_at)
                VALUES (
                    1,
                    'MAIN',
                    '2024-03-16 00:37:57.491000 +00:00'
                );
                """);

        var link = service.add(1, URI.create("https://github.com/Anoldyouth/Java-Telegram-Notifications"));

        Boolean contains = jdbcTemplate.query(
                "SELECT * FROM tg_chats_links WHERE link_id = " + link.id(),
                ResultSet::next
        );

        assertEquals(Boolean.TRUE, contains);
    }

    private Stream<LinkService> addNew() {
        return Stream.of(
                new JdbcLinkService(linkDao, tgChatLinkDao),
                new JpaLinkService(linkRepository, tgChatRepository)
        );
    }

    @ParameterizedTest
    @MethodSource
    @Transactional
    @Rollback
    void addExists(LinkService service) {
        jdbcTemplate.execute("""
                INSERT INTO links (id, url, last_check_at, created_at)
                VALUES (
                    1,
                    'https://github.com/Anoldyouth/Java-Telegram-Notifications',
                    '2023-03-16 00:37:57.491000 +00:00',
                    '2023-03-16 00:37:57.491000 +00:00'
                );

                INSERT INTO tg_chats (id, state, created_at)
                VALUES (
                    1,
                    'MAIN',
                    '2024-03-16 00:37:57.491000 +00:00'
                );
                """);

        service.add(1, URI.create("https://github.com/Anoldyouth/Java-Telegram-Notifications"));

        Boolean contains = jdbcTemplate.query(
                "SELECT * FROM tg_chats_links WHERE link_id = 1",
                ResultSet::next
        );

        assertEquals(Boolean.TRUE, contains);
    }

    private Stream<LinkService> addExists() {
        return Stream.of(
                new JdbcLinkService(linkDao, tgChatLinkDao),
                new JpaLinkService(linkRepository, tgChatRepository)
        );
    }

    @ParameterizedTest
    @MethodSource
    @Transactional
    @Rollback
    void removeSingleChatForLink(LinkService service) throws NotFoundException {
        jdbcTemplate.execute("""
                INSERT INTO links (id, url, last_check_at, created_at)
                VALUES (
                    1,
                    'https://github.com/Anoldyouth/Java-Telegram-Notifications',
                    '2024-03-16 00:37:57.491000 +00:00',
                    '2024-03-16 00:37:57.491000 +00:00'
                );
                
                INSERT INTO tg_chats (id, state, created_at)
                VALUES (
                    1,
                    'MAIN',
                    '2024-03-16 00:37:57.491000 +00:00'
                );
                
                INSERT INTO tg_chats_links (tg_chat_id, link_id)
                VALUES (1, 1);
                """);

        var link = service.remove(1, 1);
        Boolean containsPair = jdbcTemplate.query(
                "SELECT * FROM tg_chats_links WHERE link_id = 1",
                ResultSet::next
        );
        Boolean containsLink = jdbcTemplate.query(
                "SELECT * FROM links WHERE id = 1",
                ResultSet::next
        );

        assertThat(link.id()).isEqualTo(1);
        assertNotEquals(Boolean.TRUE, containsPair);
        assertNotEquals(Boolean.TRUE, containsLink);
    }

    private Stream<LinkService> removeSingleChatForLink() {
        return Stream.of(
                new JdbcLinkService(linkDao, tgChatLinkDao),
                new JpaLinkService(linkRepository, tgChatRepository)
        );
    }

    @ParameterizedTest
    @MethodSource
    @Transactional
    @Rollback
    void removeSeveralChatsForLink(LinkService service) throws NotFoundException {
        jdbcTemplate.execute("""
                INSERT INTO links (id, url, last_check_at, created_at)
                VALUES (
                    1,
                    'https://github.com/Anoldyouth/Java-Telegram-Notifications',
                    '2024-03-16 00:37:57.491000 +00:00',
                    '2024-03-16 00:37:57.491000 +00:00'
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
                VALUES (1, 1), (2, 1);
                """);

        var link = service.remove(1, 1);
        Boolean containsFirstPair = jdbcTemplate.query(
                "SELECT * FROM tg_chats_links WHERE link_id = 1 AND tg_chat_id = 1",
                ResultSet::next
        );
        Boolean containsSecondPair = jdbcTemplate.query(
                "SELECT * FROM tg_chats_links WHERE link_id = 1 AND tg_chat_id = 2",
                ResultSet::next
        );
        Boolean containsLink = jdbcTemplate.query(
                "SELECT * FROM links WHERE id = 1",
                ResultSet::next
        );

        assertThat(link.id()).isEqualTo(1);
        assertNotEquals(Boolean.TRUE, containsFirstPair);
        assertEquals(Boolean.TRUE, containsSecondPair);
        assertEquals(Boolean.TRUE, containsLink);
    }

    private Stream<LinkService> removeSeveralChatsForLink() {
        return Stream.of(
                new JdbcLinkService(linkDao, tgChatLinkDao),
                new JpaLinkService(linkRepository, tgChatRepository)
        );
    }

    @ParameterizedTest
    @MethodSource
    @Transactional
    @Rollback
    void removeLinkNotExist(LinkService service) {
        jdbcTemplate.execute("""
                INSERT INTO tg_chats (id, state, created_at)
                VALUES (
                    1,
                    'MAIN',
                    '2024-03-16 00:37:57.491000 +00:00'
                );
                """);

        assertThrows(NotFoundException.class, () -> service.remove(1, 1));
    }

    private Stream<LinkService> removeLinkNotExist() {
        return Stream.of(
                new JdbcLinkService(linkDao, tgChatLinkDao),
                new JpaLinkService(linkRepository, tgChatRepository)
        );
    }

    @ParameterizedTest
    @MethodSource
    @Transactional
    @Rollback
    void findAll(LinkService service) throws NotFoundException {
        jdbcTemplate.execute("""
                INSERT INTO links (id, url, last_check_at, created_at)
                VALUES (
                    1,
                    'https://github.com/Anoldyouth/Java-Telegram-Notifications',
                    '2024-01-16 00:37:57.491000 +00:00',
                    '2024-01-16 00:37:57.491000 +00:00'
                ), (
                    2,
                    'https://github.com/Anoldyouth/Java-Telegram-Notifications2',
                    '2024-02-16 00:37:57.491000 +00:00',
                    '2024-02-16 00:37:57.491000 +00:00'
                ), (
                    3,
                    'https://github.com/Anoldyouth/Java-Telegram-Notifications3',
                    '2024-03-16 00:37:57.491000 +00:00',
                    '2024-03-16 00:37:57.491000 +00:00'
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
                VALUES (1, 1), (1, 2), (2, 3);
                """);

        var result = service.listAll(1, 1, 1);

        assertThat(result.links().size()).isEqualTo(1);
        assertThat(result.links().getFirst().id()).isEqualTo(1);
        assertThat(result.count()).isEqualTo(2);
    }

    private Stream<LinkService> findAll() {
        return Stream.of(
                new JdbcLinkService(linkDao, tgChatLinkDao),
                new JpaLinkService(linkRepository, tgChatRepository)
        );
    }
}
