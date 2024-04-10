package edu.java.service;

import edu.java.exception.NotFoundException;
import edu.java.model.Link;
import java.net.URI;
import java.util.List;

public interface LinkService {
    Link add(long tgChatId, URI url);

    Link remove(long tgChatId, long linkId) throws NotFoundException;

    FindAllResult listAll(long tgChatId, long offset, long limit);

    record FindAllResult(List<Link> links, long count) {
    }
}
