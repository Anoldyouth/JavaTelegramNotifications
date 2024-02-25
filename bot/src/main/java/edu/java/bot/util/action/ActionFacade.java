package edu.java.bot.util.action;

import com.pengrad.telegrambot.model.Update;
import edu.java.bot.util.response.ResponseData;
import java.util.List;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

@Component
public class ActionFacade {
    public ResponseData applyScenario(@NotNull List<Action> actions, Update update) {
        return buildChain(actions).apply(update);
    }

    private Action buildChain(@NotNull List<Action> actions) {
        if (actions.isEmpty()) {
            return new UnknownCommandAction();
        }

        for (int i = 0; i < actions.size() - 1; i++) {
            actions.get(i).setNext(actions.get(i + 1));
        }

        actions.getLast().setNext(new UnknownCommandAction());

        return actions.getFirst();
    }
}
