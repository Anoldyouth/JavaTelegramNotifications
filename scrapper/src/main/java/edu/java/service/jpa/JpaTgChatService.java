package edu.java.service.jpa;

import edu.java.dao.jpa.JpaTgChatRepository;
import edu.java.dao.jpa.entity.TgChatEntity;
import edu.java.exception.NotFoundException;
import edu.java.model.TgChat;
import edu.java.service.TgChatService;
import edu.java.util.State;
import java.time.OffsetDateTime;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JpaTgChatService implements TgChatService {
    private final JpaTgChatRepository tgChatRepository;

    @Override
    public void register(long tgChatId) {
        OffsetDateTime timestamp = OffsetDateTime.now();
        var chat = new TgChatEntity();
        chat.setId(tgChatId);
        chat.setState(State.MAIN);
        chat.setCreatedAt(timestamp);

        tgChatRepository.saveAndFlush(chat);
    }

    @Override
    public void unregister(long tgChatId) {
        tgChatRepository.deleteById(tgChatId);
        tgChatRepository.flush();
    }

    @Override
    public TgChat updateState(long tgChatId, State state) throws NotFoundException {
        var chat = tgChatRepository.findById(tgChatId).orElseThrow(NotFoundException::new);
        chat.setState(state);
        tgChatRepository.saveAndFlush(chat);

        return new TgChat(chat.getId(), chat.getState(), chat.getCreatedAt());
    }

    @Override
    public TgChat get(long tgChatId) throws NotFoundException {
        var chat = tgChatRepository.findById(tgChatId).orElseThrow(NotFoundException::new);

        return new TgChat(chat.getId(), chat.getState(), chat.getCreatedAt());
    }
}
