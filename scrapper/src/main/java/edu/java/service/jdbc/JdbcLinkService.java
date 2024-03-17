package edu.java.service.jdbc;

import edu.java.dao.JdbcLinkDao;
import edu.java.dao.JdbcTgChatLinkDao;
import edu.java.model.Link;
import edu.java.service.LinkService;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class JdbcLinkService implements LinkService {
    private final JdbcLinkDao linkDao;
    private final JdbcTgChatLinkDao tgChatLinkDao;

    @Override
    public Link add(long tgChatId, URI url) {
        linkDao.add(tgChatId, url);

        return linkDao.getOneByUrl(url);
    }

    @Override
    @Transactional
    public Link remove(long tgChatId, long linkId) {
        tgChatLinkDao.remove(tgChatId, linkId);

        Link link = linkDao.getOneById(linkId);

        if (linkDao.findAll(tgChatId, 0, 1).count() == 0) {
            linkDao.remove(linkId);
        }

        return link;
    }

    @Override
    public Link.FindAllResult listAll(long tgChatId, int offset, int limit) {
        return linkDao.findAll(tgChatId, offset, limit);
    }
}
