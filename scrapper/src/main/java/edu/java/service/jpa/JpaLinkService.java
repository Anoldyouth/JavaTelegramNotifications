package edu.java.service.jpa;

import edu.java.dao.jpa.JpaLinkRepository;
import edu.java.dao.jpa.JpaTgChatRepository;
import edu.java.dao.jpa.entity.LinkEntity;
import edu.java.exception.NotFoundException;
import edu.java.model.Link;
import edu.java.service.LinkService;
import java.net.URI;
import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JpaLinkService implements LinkService {
    private final JpaLinkRepository linkRepository;
    private final JpaTgChatRepository tgChatRepository;

    @Override
    public Link add(long tgChatId, URI url) {

        var tgChat = tgChatRepository.findById(tgChatId).get();

        var link = linkRepository.findByUrl(url).orElseGet(() -> {
            OffsetDateTime timestamp = OffsetDateTime.now();
            var newLink = new LinkEntity();
            newLink.setUrl(url);
            newLink.setCreatedAt(timestamp);
            newLink.setLastCheckAt(timestamp);
            newLink.setChats(new HashSet<>());

            return newLink;
        });
        link.getChats().add(tgChat);

        linkRepository.saveAndFlush(link);

        return new Link(
                link.getId(),
                link.getUrl(),
                link.getLastCheckAt(),
                link.getCreatedAt()
        );
    }

    @Override
    public Link remove(long tgChatId, long linkId) throws NotFoundException {
        var link = linkRepository.findById(linkId).orElseThrow(NotFoundException::new);
        var tgChat = tgChatRepository.findById(tgChatId).orElseThrow(NotFoundException::new);

        link.getChats().remove(tgChat);

        if (link.getChats().isEmpty()) {
            linkRepository.delete(link);
            linkRepository.flush();
        } else {
            linkRepository.saveAndFlush(link);
        }

        return new Link(
                link.getId(),
                link.getUrl(),
                link.getLastCheckAt(),
                link.getCreatedAt()
        );
    }

    @Override
    public FindAllResult listAll(long tgChatId, long offset, long limit) {
        List<Link> links = linkRepository.findByChatId(tgChatId, offset, limit)
                .stream()
                .map(linkEntity -> new Link(
                        linkEntity.getId(),
                        linkEntity.getUrl(),
                        linkEntity.getLastCheckAt(),
                        linkEntity.getCreatedAt())
                )
                .toList();

        long count = linkRepository.countByTgChatId(tgChatId);

        return new FindAllResult(links, count);
    }
}
