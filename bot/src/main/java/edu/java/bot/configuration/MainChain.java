package edu.java.bot.configuration;

import edu.java.bot.util.action.Action;
import edu.java.bot.util.action.CancelAction;
import edu.java.bot.util.action.HelpAction;
import edu.java.bot.util.action.ListAction;
import edu.java.bot.util.action.StartAction;
import edu.java.bot.util.action.TrackAction;
import edu.java.bot.util.action.TrackUrlAction;
import edu.java.bot.util.action.UntrackAction;
import edu.java.bot.util.action.UntrackUrlAction;
import java.util.List;

public class MainChain implements Chain {
    @Override
    public List<Action> getChain() {
        return List.of(
                new CancelAction(), // временно, для тестов. Убрать после подключения БД
                new StartAction(),
                new HelpAction(),
                new TrackAction(),
                new UntrackAction(),
                new ListAction(),
                new TrackUrlAction(), // временно, для тестов. Убрать после подключения БД
                new UntrackUrlAction() // временно, для тестов. Убрать после подключения БД
        );
    }
}
