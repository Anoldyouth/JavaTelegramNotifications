package edu.java.dao.jpa;

import edu.java.dao.jpa.entity.TgChatEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaTgChatRepository extends JpaRepository<TgChatEntity, Long> {
}
