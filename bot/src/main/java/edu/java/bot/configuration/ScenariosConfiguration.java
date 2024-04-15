package edu.java.bot.configuration;

import edu.java.bot.util.ScenarioDispatcher;
import edu.java.bot.util.action.Action;
import edu.java.bot.util.action.CancelAction;
import edu.java.bot.util.action.HelpAction;
import edu.java.bot.util.action.ListAction;
import edu.java.bot.util.action.ListShowMoreAction;
import edu.java.bot.util.action.StartAction;
import edu.java.bot.util.action.TrackAction;
import edu.java.bot.util.action.TrackUrlAction;
import edu.java.bot.util.action.UntrackAction;
import edu.java.bot.util.action.UntrackShowMoreAction;
import edu.java.bot.util.action.UntrackUrlAction;
import java.util.List;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@SuppressWarnings("ParameterNumber")
public class ScenariosConfiguration {
    @Qualifier("startScenario")
    @Bean
    public List<Action> startScenario(
            StartAction startAction
    ) {
        return List.of(
                startAction
        );
    }

    @Qualifier("mainScenario")
    @Bean
    public List<Action> mainScenario(
            HelpAction helpAction,
            TrackAction trackAction,
            UntrackAction untrackAction,
            ListAction listAction
    ) {
        return List.of(
                helpAction,
                trackAction,
                untrackAction,
                listAction
        );
    }

    @Qualifier("trackUrlScenario")
    @Bean
    public List<Action> trackUrlScenario(
            CancelAction cancelAction,
            TrackUrlAction trackUrlAction
    ) {
        return List.of(
                cancelAction,
                trackUrlAction
        );
    }

    @Qualifier("untrackUrlScenario")
    @Bean
    public List<Action> untrackUrlScenario(
            CancelAction cancelAction,
            UntrackUrlAction untrackUrlAction,
            UntrackShowMoreAction untrackShowMoreAction
    ) {
        return List.of(
                cancelAction,
                untrackShowMoreAction,
                untrackUrlAction
        );
    }

    @Qualifier("listScenario")
    @Bean
    public List<Action> listScenario(
            CancelAction cancelAction,
            ListShowMoreAction listShowMoreAction
    ) {
        return List.of(
                cancelAction,
                listShowMoreAction
        );
    }

    @Bean
    public ScenarioDispatcher scenarioDispatcher() {
        return new ScenarioDispatcher();
    }
}
