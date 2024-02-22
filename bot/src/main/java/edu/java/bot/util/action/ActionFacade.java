package edu.java.bot.util.action;

import com.pengrad.telegrambot.model.Update;
import edu.java.bot.dto.response.ResponseData;
import java.util.List;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

@Service
public class ActionFacade {
    private final Action chainHead;

    public ActionFacade(@NotNull List<Action> actions) {
        if (actions.isEmpty()) {
            this.chainHead = new UnknownCommandAction();

            return;
        }

        for (int i = 0; i < actions.size() - 1; i++) {
            actions.get(i).setNext(actions.get(i + 1));
        }

        actions.getLast().setNext(new UnknownCommandAction());

        this.chainHead = actions.getFirst();
    }

    public ResponseData apply(Update update) {
        return chainHead.apply(update);
    }
}
