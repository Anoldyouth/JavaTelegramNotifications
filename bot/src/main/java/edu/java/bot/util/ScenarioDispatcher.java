package edu.java.bot.util;

import edu.java.bot.util.action.Action;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class ScenarioDispatcher {
    @Autowired
    @Qualifier("startScenario")
    private List<Action> startScenario;

    @Autowired
    @Qualifier("mainScenario")
    private List<Action> mainScenario;

    @Autowired
    @Qualifier("trackUrlScenario")
    private List<Action> trackUrlScenario;

    @Autowired
    @Qualifier("untrackUrlScenario")
    private List<Action> untrackUrlScenario;

    @Autowired
    @Qualifier("listScenario")
    private List<Action> listScenario;

    public enum ScenarioType {
        START,
        MAIN,
        TRACK_URL,
        UNTRACK_URL,
        LIST
    }

    public List<Action> getScenario(ScenarioType type) {
        return switch (type) {
            case START -> startScenario;
            case MAIN -> mainScenario;
            case TRACK_URL -> trackUrlScenario;
            case UNTRACK_URL -> untrackUrlScenario;
            case LIST -> listScenario;
        };
    }
}
