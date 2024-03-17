package edu.java.dao;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JdbcTgChatLinkDao {
    private final JdbcTemplate jdbcTemplate;

    public List<Long> getTgChatIdsByLinkId(long linkId) {
        String query = """
                SELECT  tg_chat_id
                FROM tg_chats_links
                WHERE link_id = ?
                """;

        return jdbcTemplate.query(
                query,
                preparedStatement -> {
                    preparedStatement.setLong(1, linkId);
                },
                new BeanPropertyRowMapper<>()
        );
    }

    public void remove(long tgChatId, long linkId) {
        jdbcTemplate.update("DELETE FROM tg_chats_links WHERE tg_chat_id = ? AND link_id = ?", tgChatId, linkId);
    }
}
