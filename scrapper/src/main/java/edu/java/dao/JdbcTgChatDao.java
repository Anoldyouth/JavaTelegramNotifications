package edu.java.dao;

import edu.java.model.TgChat;
import edu.java.util.State;
import java.sql.PreparedStatement;
import java.time.OffsetDateTime;
import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@SuppressWarnings({"MagicNumber", "MultipleStringLiterals"})
public class JdbcTgChatDao {
    private final JdbcTemplate jdbcTemplate;

    public void add(long tgChatId, @NotNull State state) {
        OffsetDateTime timestamp = OffsetDateTime.now();

        String sql = "INSERT INTO tg_chats (id, state, created_at) VALUES (?, ?, ?)";

        jdbcTemplate.update(con -> {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setLong(1, tgChatId);
            preparedStatement.setString(2, state.name());
            preparedStatement.setObject(3, timestamp);

            return preparedStatement;
        });
    }

    public void remove(long tgChatId) {
        String sql = "DELETE FROM tg_chats WHERE id = ?";

        jdbcTemplate.update(con -> {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setLong(1, tgChatId);

            return preparedStatement;
        });
    }

    public TgChat getOneById(long tgChatId) {
        String sql = "SELECT * FROM tg_chats WHERE id = ? LIMIT 1";

        return jdbcTemplate.execute(sql, (PreparedStatementCallback<TgChat>) preparedStatement -> {
            preparedStatement.setLong(1, tgChatId);

            var result = preparedStatement.executeQuery();

            if (result.next()) {
                return new TgChat(
                        result.getLong("id"),
                        State.valueOf(result.getString("state")),
                        result.getObject("created_at", OffsetDateTime.class)
                );
            }

            return null;
        });
    }

    public void update(long tgChatId, @NotNull State state) {
        String sql = "UPDATE tg_chats SET state = ? WHERE id = ?";

        jdbcTemplate.update(con -> {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, state.name());
            preparedStatement.setLong(2, tgChatId);

            return preparedStatement;
        });
    }
}
