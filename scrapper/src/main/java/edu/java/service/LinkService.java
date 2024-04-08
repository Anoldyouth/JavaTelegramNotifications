package edu.java.service;

import edu.java.exception.NotFoundException;
import edu.java.model.Link;
import java.net.URI;

public interface LinkService {
    Link add(long tgChatId, URI url);

    Link remove(long tgChatId, long linkId) throws NotFoundException;

    Link.FindAllResult listAll(long tgChatId, long offset, long limit);
}
