package edu.java.scrapper;

import edu.java.dao.jdbc.JdbcTgChatDao;
import edu.java.dao.jpa.JpaTgChatRepository;
import edu.java.exception.NotFoundException;
import edu.java.service.TgChatService;
import edu.java.service.jdbc.JdbcTgChatService;
import edu.java.service.jpa.JpaTgChatService;
import edu.java.util.State;
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
public class TgChatServiceIntegrationTest extends IntegrationTest {
    @Autowired
    private JdbcTgChatDao tgChatDao;

    @Autowired
    private JpaTgChatRepository tgChatRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @ParameterizedTest
    @MethodSource
    @Transactional
    @Rollback
    void register(TgChatService service) {
        service.register(1);

        Boolean contains = jdbcTemplate.query("SELECT * FROM tg_chats WHERE id = 1", ResultSet::next);

        assertEquals(Boolean.TRUE, contains);
    }

    private Stream<TgChatService> register() {
        return Stream.of(
                new JdbcTgChatService(tgChatDao),
                new JpaTgChatService(tgChatRepository)
        );
    }

    @ParameterizedTest
    @MethodSource
    @Transactional
    @Rollback
    void unregister(TgChatService service) {
        jdbcTemplate.execute("""
                INSERT INTO tg_chats (id, state, created_at)
                VALUES (
                    1,
                    'MAIN',
                    '2024-03-16 00:37:57.491000 +00:00'
                );
                """);

        service.unregister(1);

        Boolean contains = jdbcTemplate.query("SELECT * FROM tg_chats WHERE id = 1", ResultSet::next);

        assertNotEquals(Boolean.TRUE, contains);
    }

    private Stream<TgChatService> unregister() {
        return Stream.of(
                new JdbcTgChatService(tgChatDao),
                new JpaTgChatService(tgChatRepository)
        );
    }

    @ParameterizedTest
    @MethodSource
    @Transactional
    @Rollback
    void updateStateChatExist(TgChatService service) throws NotFoundException {
        jdbcTemplate.execute("""
                INSERT INTO tg_chats (id, state, created_at)
                VALUES (
                    1,
                    'MAIN',
                    '2024-03-16 00:37:57.491000 +00:00'
                );
                """);

        service.updateState(1, State.TRACK_URL);

        Boolean contains = jdbcTemplate.query(
                "SELECT * FROM tg_chats WHERE id = 1 AND state = 'TRACK_URL'",
                ResultSet::next
        );

        assertEquals(Boolean.TRUE, contains);
    }

    private Stream<TgChatService> updateStateChatExist() {
        return Stream.of(
                new JdbcTgChatService(tgChatDao),
                new JpaTgChatService(tgChatRepository)
        );
    }

    @ParameterizedTest
    @MethodSource
    @Transactional
    @Rollback
    void updateStateChatNotExist(TgChatService service) {
        assertThrows(NotFoundException.class, () -> service.updateState(1, State.TRACK_URL));
    }

    private Stream<TgChatService> updateStateChatNotExist() {
        return Stream.of(
                new JdbcTgChatService(tgChatDao),
                new JpaTgChatService(tgChatRepository)
        );
    }

    @ParameterizedTest
    @MethodSource
    @Transactional
    @Rollback
    void getChatExist(TgChatService service) throws NotFoundException {
        jdbcTemplate.execute("""
                INSERT INTO tg_chats (id, state, created_at)
                VALUES (
                    1,
                    'MAIN',
                    '2024-03-16 00:37:57.491000 +00:00'
                );
                """);

        var chat = service.get(1);

        assertThat(chat.id()).isEqualTo(1);
        assertThat(chat.state()).isEqualTo(State.MAIN);
    }

    private Stream<TgChatService> getChatExist() {
        return Stream.of(
                new JdbcTgChatService(tgChatDao),
                new JpaTgChatService(tgChatRepository)
        );
    }

    @ParameterizedTest
    @MethodSource
    @Transactional
    @Rollback
    void getChatNotExist(TgChatService service) {
        assertThrows(NotFoundException.class, () -> service.get(1));
    }

    private Stream<TgChatService> getChatNotExist() {
        return Stream.of(
                new JdbcTgChatService(tgChatDao),
                new JpaTgChatService(tgChatRepository)
        );
    }

}
