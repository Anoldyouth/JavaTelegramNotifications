package edu.java.bot.configuration;

import edu.java.bot.util.ScenarioDispatcher;
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
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ScenariosConfiguration {
    @SuppressWarnings("ParameterNumber")
    @Qualifier("mainScenario")
    @Bean
    public List<Action> mainScenario(
            CancelAction cancelAction, // временно, для тестов. Убрать после подключения БД
            StartAction startAction,
            HelpAction helpAction,
            TrackAction trackAction,
            UntrackAction untrackAction,
            ListAction listAction,
            TrackUrlAction trackUrlAction, // временно, для тестов. Убрать после подключения БД
            UntrackUrlAction untrackUrlAction // временно, для тестов. Убрать после подключения БД
    ) {
        return List.of(
                cancelAction,
                startAction,
                helpAction,
                trackAction,
                untrackAction,
                listAction,
                trackUrlAction,
                untrackUrlAction
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
            UntrackUrlAction untrackUrlAction
    ) {
        return List.of(
                cancelAction,
                untrackUrlAction
        );
    }

    @Bean
    public ScenarioDispatcher scenarioDispatcher() {
        return new ScenarioDispatcher();
    }
}
