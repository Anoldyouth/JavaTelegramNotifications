package edu.java.bot.configuration;

import edu.java.bot.util.action.Action;
import edu.java.bot.util.action.CancelAction;
import edu.java.bot.util.action.UntrackUrlAction;
import java.util.List;

public class UntrackUrlChain implements Chain {
    @Override
    public List<Action> getChain() {
        return List.of(
                new CancelAction(),
                new UntrackUrlAction()
        );
    }
}
