package edu.java.scrapper;

import edu.java.dao.jdbc.JdbcTgChatLinkDao;
import java.sql.ResultSet;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
public class JdbcTgChatLinkDaoTest extends IntegrationTest {
    @Autowired
    private JdbcTgChatLinkDao tgChatLinkDao;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    @Transactional
    @Rollback
    void remove() {
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
                    2,
                    'MAIN',
                    '2024-03-16 00:37:57.491000 +00:00'
                );
                                
                INSERT INTO tg_chats_links (tg_chat_id, link_id)
                VALUES (2, 1);
                """);

        tgChatLinkDao.remove(2, 1);
        Boolean contains = jdbcTemplate.query("SELECT * FROM tg_chats_links", ResultSet::next);

        assertEquals(Boolean.FALSE, contains);
    }

    @Test
    @Transactional
    @Rollback
    void getTgChatIdsByLinkId() {
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
                    2,
                    'MAIN',
                    '2024-03-16 00:37:57.491000 +00:00'
                );
                                
                INSERT INTO tg_chats_links (tg_chat_id, link_id)
                VALUES (2, 1);
                """);

        var list = tgChatLinkDao.getTgChatIdsByLinkId(1);
        assertThat(list).isEqualTo(List.of(2L));
    }
}
