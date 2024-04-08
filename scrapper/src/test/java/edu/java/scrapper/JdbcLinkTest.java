package edu.java.scrapper;

import edu.java.configuration.properties.ApplicationConfig;
import edu.java.dao.JdbcLinkDao;
import java.net.URI;
import java.sql.ResultSet;
import java.time.OffsetDateTime;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
public class JdbcLinkTest extends IntegrationTest {
    @Autowired
    private JdbcLinkDao linkDao;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    ApplicationConfig applicationConfig;

    @Test
    @Transactional
    @Rollback
    void addTest() {
        jdbcTemplate.execute("""
                INSERT INTO tg_chats (id, state, created_at)
                VALUES (
                    1,
                    'MAIN',
                    '2024-03-16 00:37:57.491000 +00:00'
                );
                """);

        linkDao.add(1, URI.create("https://github.com/Anoldyouth/Java-Telegram-Notifications"));
        Boolean contains = jdbcTemplate.query("SELECT * FROM tg_chats_links WHERE link_id = 1", ResultSet::next);

        assertEquals(Boolean.TRUE, contains);
    }

    @Test
    @Transactional
    @Rollback
    void addLinkExistTest() {
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

        linkDao.add(1, URI.create("https://github.com/Anoldyouth/Java-Telegram-Notifications"));
        Boolean contains = jdbcTemplate.query("SELECT * FROM tg_chats_links WHERE link_id = 1", ResultSet::next);

        assertEquals(Boolean.TRUE, contains);
    }

    @Test
    @Transactional
    @Rollback
    void removeTest() {
        jdbcTemplate.execute("""
                INSERT INTO links (id, url, last_check_at, created_at)
                VALUES (
                    1,
                    'https://github.com/Anoldyouth/Java-Telegram-Notifications',
                    '2024-03-16 00:37:57.491000 +00:00',
                    '2024-03-16 00:37:57.491000 +00:00'
                )
                """);

        linkDao.remove(1L);
        Boolean contains = jdbcTemplate.query("SELECT * FROM links WHERE id = 1", ResultSet::next);

        assertNotEquals(Boolean.TRUE, contains);
    }

    @Test
    @Transactional
    @Rollback
    void removeNonExistTest() {
        linkDao.remove(1L);

        Boolean contains = jdbcTemplate.query("SELECT * FROM links WHERE id = 1", ResultSet::next);
        assertNotEquals(Boolean.TRUE, contains);
    }

    @Test
    @Transactional
    @Rollback
    void findAllTest() {
        jdbcTemplate.execute("""
                INSERT INTO links (id, url, last_check_at, created_at)
                VALUES (
                    1,
                    'https://github.com/Anoldyouth/Java-Telegram-Notifications',
                    '2023-03-16 00:37:57.491000 +00:00',
                    '2023-03-16 00:37:57.491000 +00:00'
                );
                
                INSERT INTO links (id, url, last_check_at, created_at)
                VALUES (
                    2,
                    'https://github.com',
                    '2024-03-16 00:37:57.491000 +00:00',
                    '2024-03-16 00:37:57.491000 +00:00'
                );
                
                INSERT INTO tg_chats (id, state, created_at)
                VALUES (
                    2,
                    'MAIN',
                    '2024-03-16 00:37:57.491000 +00:00'
                );
                
                INSERT INTO tg_chats_links (tg_chat_id, link_id)
                VALUES (2, 1);
                
                INSERT INTO tg_chats_links (tg_chat_id, link_id)
                VALUES (2, 2);
                """);

        var result = linkDao.findAll(2, 0, 1);

        assertThat(result.links().size()).isEqualTo(1);
        assertThat(result.links().getFirst().id()).isEqualTo(2);
        assertThat(result.count()).isEqualTo(2);
    }

    @Test
    @Transactional
    @Rollback
    void getAllWhereLastCheckAtBeforeTest() {
        jdbcTemplate.execute("""
                INSERT INTO links (id, url, last_check_at, created_at)
                VALUES (
                    1,
                    'https://github.com/Anoldyouth/Java-Telegram-Notifications',
                    '2023-03-16 00:37:57.491000 +00:00',
                    '2023-03-16 00:37:57.491000 +00:00'
                ), (
                    2,
                    'https://github.com/Anoldyouth/Java-Telegram-Notifications2',
                    '2024-03-16 00:37:57.491000 +00:00',
                    '2024-03-16 00:37:57.491000 +00:00'
                )
                """);

        var result = linkDao.getAllWhereLastCheckAtBefore(
                OffsetDateTime.parse("2024-01-01T00:00:00+00:00"),
                0,
                2
        );

        assertThat(result.size()).isEqualTo(1);
        assertThat(result.getFirst().id()).isEqualTo(1);
    }

    @Test
    @Transactional
    @Rollback
    void getOneByIdTest() {
        jdbcTemplate.execute("""
                INSERT INTO links (id, url, last_check_at, created_at)
                VALUES (
                    1,
                    'https://github.com/Anoldyouth/Java-Telegram-Notifications',
                    '2023-03-16 00:37:57.491000 +00:00',
                    '2023-03-16 00:37:57.491000 +00:00'
                )
                """);

        var link = linkDao.getOneById(1);

        assertThat(link.url().toString()).isEqualTo("https://github.com/Anoldyouth/Java-Telegram-Notifications");
    }

    @Test
    @Transactional
    @Rollback
    void getOneByUrlTest() {
        jdbcTemplate.execute("""
                INSERT INTO links (id, url, last_check_at, created_at)
                VALUES (
                    1,
                    'https://github.com/Anoldyouth/Java-Telegram-Notifications',
                    '2023-03-16 00:37:57.491000 +00:00',
                    '2023-03-16 00:37:57.491000 +00:00'
                )
                """);

        var link = linkDao.getOneByUrl(URI.create("https://github.com/Anoldyouth/Java-Telegram-Notifications"));

        assertThat(link.id()).isEqualTo(1);
    }

    @Test
    @Transactional
    @Rollback
    void updateTest() {
        jdbcTemplate.execute("""
                INSERT INTO links (id, url, last_check_at, created_at)
                VALUES (
                    1,
                    'https://github.com/Anoldyouth/Java-Telegram-Notifications',
                    '2023-03-16 00:37:57.491000 +00:00',
                    '2023-03-16 00:37:57.491000 +00:00'
                )
                """);

        var lastCheckAt = OffsetDateTime.parse("2024-01-01T00:00:00+00:00");

        linkDao.update(1, lastCheckAt);
        var link = linkDao.getOneById(1);

        assertThat(link.lastCheckAt()).isEqualTo(lastCheckAt);
    }
}
