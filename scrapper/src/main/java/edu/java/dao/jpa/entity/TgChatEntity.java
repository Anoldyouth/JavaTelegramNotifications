package edu.java.dao.jpa.entity;

import edu.java.util.State;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import java.time.OffsetDateTime;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "tg_chats")
@Getter @Setter
public class TgChatEntity {
    @Id
    private long id;

    @Enumerated(EnumType.STRING)
    private State state;

    private OffsetDateTime createdAt;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "tg_chats_links",
            joinColumns = @JoinColumn(name = "tg_chat_id"),
            inverseJoinColumns = @JoinColumn(name = "link_id")
    )
    private Set<LinkEntity> links;
}
