package edu.java.dao.jdbc;

import edu.java.model.Link;
import edu.java.service.LinkService;
import java.net.URI;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.OffsetDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@AllArgsConstructor
@SuppressWarnings({"MagicNumber", "MultipleStringLiterals"})
public class JdbcLinkDao {
    private final JdbcTemplate jdbcTemplate;

    @Transactional
    public void add(long tgChatId, URI url) {
        OffsetDateTime timestamp = OffsetDateTime.now();

        jdbcTemplate.update(con -> {
            PreparedStatement preparedStatement = con.prepareStatement("""
                    INSERT INTO links (url, last_check_at, created_at)
                    VALUES (?, ?, ?)
                    ON CONFLICT DO NOTHING;
                    """);
            preparedStatement.setString(1, url.toString());
            preparedStatement.setObject(2, timestamp);
            preparedStatement.setObject(3, timestamp);

            return preparedStatement;
        });

        jdbcTemplate.update(con -> {
            PreparedStatement preparedStatement = con.prepareStatement("""
                    INSERT INTO tg_chats_links (tg_chat_id, link_id)
                    SELECT ?, id
                    FROM links
                    WHERE url = ?
                    ON CONFLICT DO NOTHING;
                    """);
            preparedStatement.setLong(1, tgChatId);
            preparedStatement.setString(2, url.toString());

            return preparedStatement;
        });
    }

    public void remove(long id) {
        jdbcTemplate.update("DELETE FROM links WHERE id = ?", id);
    }

    @Transactional
    public LinkService.FindAllResult findAll(
            long tgChatId,
            long offset,
            long limit
    ) {
        String query = """
                SELECT l.*
                FROM tg_chats_links tcl JOIN links l on l.id = tcl.link_id
                WHERE tg_chat_id = ?
                ORDER BY created_at desc
                OFFSET ?
                LIMIT ?
                """;

        List<Link> result = jdbcTemplate.query(
                query,
                preparedStatement -> {
                    preparedStatement.setLong(1, tgChatId);
                    preparedStatement.setLong(2, offset);
                    preparedStatement.setLong(3, limit);
                },
                new LinkRowMapper());

        String countQuery = "SELECT COUNT(*) FROM tg_chats_links WHERE tg_chat_id = " + tgChatId;

        long total = jdbcTemplate.queryForObject(countQuery, Long.class);

        return new LinkService.FindAllResult(result, total);
    }

    public List<Link> getAllWhereLastCheckAtBefore(
            OffsetDateTime before,
            int offset,
            int limit
    ) {
        String query = """
                SELECT *
                FROM links
                WHERE last_check_at < ?
                ORDER BY id
                OFFSET ?
                LIMIT ?
                """;

        return jdbcTemplate.query(
                query,
                preparedStatement -> {
                    preparedStatement.setObject(1, before);
                    preparedStatement.setInt(2, offset);
                    preparedStatement.setInt(3, limit);
                },
                new LinkRowMapper());
    }

    public Link getOneById(long id) {
        return jdbcTemplate.execute(
                "SELECT * FROM links WHERE id = ? LIMIT 1",
                (PreparedStatementCallback<Link>) preparedStatement -> {
                    preparedStatement.setLong(1, id);

                    var result = preparedStatement.executeQuery();

                    if (result.next()) {
                        return new Link(
                                result.getLong("id"),
                                URI.create(result.getString("url")),
                                result.getObject("last_check_at", OffsetDateTime.class),
                                result.getObject("created_at", OffsetDateTime.class)
                        );
                    }

                    return null;
                }
        );
    }

    public Link getOneByUrl(URI url) {
        return jdbcTemplate.execute(
                "SELECT * FROM links WHERE url = ? LIMIT 1",
                (PreparedStatementCallback<Link>) preparedStatement -> {
                    preparedStatement.setString(1, url.toString());

                    var result = preparedStatement.executeQuery();

                    if (result.next()) {
                        return new Link(
                                result.getLong("id"),
                                URI.create(result.getString("url")),
                                result.getObject("last_check_at", OffsetDateTime.class),
                                result.getObject("created_at", OffsetDateTime.class)
                        );
                    }

                    return null;
                }
        );
    }

    public void update(long id, @NotNull OffsetDateTime lastCheckAt) {
        String sql = "UPDATE links SET last_check_at = ? WHERE id = ?";

        jdbcTemplate.update(con -> {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setObject(1, lastCheckAt);
            preparedStatement.setLong(2, id);

            return preparedStatement;
        });
    }

    private static class LinkRowMapper implements RowMapper<Link> {
        @Override
        public Link mapRow(ResultSet rs, int rowNum) throws SQLException {
            long id = rs.getLong("id");
            URI url = URI.create(rs.getString("url"));
            OffsetDateTime lastCheckAt = rs.getObject("last_check_at", OffsetDateTime.class);
            OffsetDateTime createdAt = rs.getObject("created_at", OffsetDateTime.class);

            return new Link(id, url, lastCheckAt, createdAt);
        }
    }
}
