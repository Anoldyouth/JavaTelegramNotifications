package edu.java.service;

import edu.java.model.Link;
import java.net.URI;

public interface LinkService {
    Link add(long tgChatId, URI url);

    Link remove(long tgChatId, long linkId);

    Link.FindAllResult listAll(long tgChatId, int offset, int limit);
}
