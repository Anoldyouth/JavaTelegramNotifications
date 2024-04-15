package edu.java.dao.jpa;

import edu.java.dao.jpa.entity.LinkEntity;
import java.net.URI;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface JpaLinkRepository extends JpaRepository<LinkEntity, Long> {
    Optional<LinkEntity> findByUrl(URI url);

    @Query(value = "SELECT l.* "
            + "FROM links l JOIN tg_chats_links tcl ON l.id = tcl.link_id "
            + "WHERE tcl.tg_chat_id = ?1 "
            + "ORDER BY l.created_at DESC "
            + "OFFSET ?2 LIMIT ?3",
            nativeQuery = true)
    List<LinkEntity> findByChatId(Long tgChatId, long offset, long limit);

    @Query(
            value = "SELECT COUNT(l) FROM links l JOIN tg_chats_links tcl ON l.id = tcl.link_id "
                    + "WHERE tcl.tg_chat_id = :tgChatId",
            nativeQuery = true)
    long countByTgChatId(Long tgChatId);

    List<LinkEntity> findByCreatedAtLessThan(OffsetDateTime timestamp, Pageable pageable);
}
