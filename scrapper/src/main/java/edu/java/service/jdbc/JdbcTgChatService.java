package edu.java.service.jdbc;

import edu.java.dao.JdbcTgChatDao;
import edu.java.exception.NotFoundException;
import edu.java.model.TgChat;
import edu.java.service.TgChatService;
import edu.java.util.State;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class JdbcTgChatService implements TgChatService {
    private final JdbcTgChatDao tgChatDao;

    @Override
    public void register(long tgChatId) {
        tgChatDao.add(tgChatId, State.MAIN);
    }

    @Override
    public void unregister(long tgChatId) {
        tgChatDao.remove(tgChatId);
    }

    @Override
    @Transactional
    public TgChat updateState(long tgChatId, State state) throws NotFoundException {
        tgChatDao.update(tgChatId, state);

        return get(tgChatId);
    }

    @Override
    public TgChat get(long tgChatId) throws NotFoundException {
        var chat = tgChatDao.getOneById(tgChatId);

        if (chat == null) {
            throw new NotFoundException();
        }

        return chat;
    }
}
