package edu.java.scrapper;

import edu.java.configuration.properties.ApplicationConfig;
import edu.java.dao.jdbc.JdbcTgChatDao;
import edu.java.util.State;
import java.sql.ResultSet;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
public class JdbcTgChatDaoTest extends IntegrationTest {
    @Autowired
    private JdbcTgChatDao tgChatDao;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    ApplicationConfig applicationConfig;

    @Test
    @Transactional
    @Rollback
    void addTest() {
        tgChatDao.add(1, State.MAIN);

        Boolean contains = jdbcTemplate.query("SELECT * FROM tg_chats WHERE id = 1", ResultSet::next);

        assertEquals(Boolean.TRUE, contains);
    }

    @Test
    @Transactional
    @Rollback
    void removeTest() {
        jdbcTemplate.execute("""
                INSERT INTO tg_chats (id, state, created_at)
                VALUES (1, 'MAIN', '2024-03-16 00:37:57.491000 +00:00')
                """);

        tgChatDao.remove(1);
        Boolean contains = jdbcTemplate.query("SELECT * FROM tg_chats WHERE id = 1", ResultSet::next);

        assertNotEquals(Boolean.TRUE, contains);
    }

    @Test
    @Transactional
    @Rollback
    void removeNonExistTest() {
        assertDoesNotThrow(() -> tgChatDao.remove(1L));
    }

    @Test
    @Transactional
    @Rollback
    void getOneTest() {
        jdbcTemplate.execute("""
                INSERT INTO tg_chats (id, state, created_at)
                VALUES (1, 'MAIN', '2024-03-16 00:37:57.491000 +00:00')
                """);

        var result = tgChatDao.getOneById(1);

        assertThat(result.id()).isEqualTo(1);
    }

    @Test
    @Transactional
    @Rollback
    void getOneNonExistTest() {
        var result = tgChatDao.getOneById(1);

        assertThat(result).isNull();
    }

    @Test
    @Transactional
    @Rollback
    void updateTest() {
        jdbcTemplate.execute("""
                INSERT INTO tg_chats (id, state, created_at)
                VALUES (1, 'MAIN', '2024-03-16 00:37:57.491000 +00:00')
                """);

        tgChatDao.update(1, State.TRACK_URL);
        Boolean contains = jdbcTemplate.query(
                "SELECT * FROM tg_chats WHERE id = 1 AND state = 'TRACK_URL'",
                ResultSet::next
        );

        assertEquals(Boolean.TRUE, contains);
    }
}
