package edu.java.dao.jpa.entity;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Converter;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import java.net.URI;
import java.time.OffsetDateTime;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "links")
@Getter @Setter
public class LinkEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "url", columnDefinition = "TEXT")
    @Convert(converter = URIConverter.class)
    private URI url;

    private OffsetDateTime lastCheckAt;

    private OffsetDateTime createdAt;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "tg_chats_links",
            joinColumns = @JoinColumn(name = "link_id"),
            inverseJoinColumns = @JoinColumn(name = "tg_chat_id")
    )
    private Set<TgChatEntity> chats;

    @Converter
    private static class URIConverter implements AttributeConverter<URI, String> {

        @Override
        public String convertToDatabaseColumn(URI uri) {
            return uri == null ? null : uri.toString();
        }

        @Override
        public URI convertToEntityAttribute(String uriString) {
            return uriString == null ? null : URI.create(uriString);
        }
    }
}
