package edu.java.bot.configuration;

import edu.java.bot.util.Chain;
import edu.java.bot.util.ChainMapper;
import edu.java.bot.util.action.CancelAction;
import edu.java.bot.util.action.HelpAction;
import edu.java.bot.util.action.ListAction;
import edu.java.bot.util.action.StartAction;
import edu.java.bot.util.action.TrackAction;
import edu.java.bot.util.action.TrackUrlAction;
import edu.java.bot.util.action.UntrackAction;
import edu.java.bot.util.action.UntrackUrlAction;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ChainsConfiguration {
    @Qualifier("mainChain")
    @Bean
    public Chain mainChain(
            CancelAction cancelAction, // временно, для тестов. Убрать после подключения БД
            StartAction startAction,
            HelpAction helpAction,
            TrackAction trackAction,
            UntrackAction untrackAction,
            ListAction listAction,
            TrackUrlAction trackUrlAction, // временно, для тестов. Убрать после подключения БД
            UntrackUrlAction untrackUrlAction // временно, для тестов. Убрать после подключения БД
    ) {
        return new Chain(
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

    @Qualifier("trackUrlChain")
    @Bean
    public Chain trackUrlChain(
            CancelAction cancelAction,
            TrackUrlAction trackUrlAction
    ) {
        return new Chain(
                cancelAction,
                trackUrlAction
        );
    }

    @Qualifier("untrackUrlChain")
    @Bean
    public Chain untrackUrlChain(
            CancelAction cancelAction,
            UntrackUrlAction untrackUrlAction
    ) {
        return new Chain(
                cancelAction,
                untrackUrlAction
        );
    }

    @Bean
    public ChainMapper chainMapper() {
        return new ChainMapper();
    }
}
