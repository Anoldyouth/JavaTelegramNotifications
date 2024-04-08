package edu.java.service.jdbc;

import edu.java.dao.JdbcLinkDao;
import edu.java.dao.JdbcTgChatLinkDao;
import edu.java.exception.NotFoundException;
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
    public Link remove(long tgChatId, long linkId) throws NotFoundException {
        Link link = linkDao.getOneById(linkId);

        if (link == null) {
            throw new NotFoundException();
        }

        tgChatLinkDao.remove(tgChatId, linkId);

        if (tgChatLinkDao.getTgChatIdsByLinkId(linkId).isEmpty()) {
            linkDao.remove(linkId);
        }

        return link;
    }

    @Override
    public Link.FindAllResult listAll(long tgChatId, long offset, long limit) {
        return linkDao.findAll(tgChatId, offset, limit);
    }
}
