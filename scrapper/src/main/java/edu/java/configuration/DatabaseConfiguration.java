package edu.java.configuration;

import edu.java.dao.jdbc.JdbcLinkDao;
import edu.java.dao.jdbc.JdbcTgChatDao;
import edu.java.dao.jdbc.JdbcTgChatLinkDao;
import edu.java.dao.jpa.JpaLinkRepository;
import edu.java.dao.jpa.JpaTgChatRepository;
import edu.java.service.LinkService;
import edu.java.service.LinkUpdater;
import edu.java.service.TgChatService;
import edu.java.service.jdbc.JdbcLinkService;
import edu.java.service.jdbc.JdbcLinkUpdater;
import edu.java.service.jdbc.JdbcTgChatService;
import edu.java.service.jpa.JpaLinkService;
import edu.java.service.jpa.JpaLinkUpdater;
import edu.java.service.jpa.JpaTgChatService;
import edu.java.service.send_link_update.SendLinkUpdate;
import edu.java.util.GithubUpdatesExtractor;
import edu.java.util.StackOverflowUpdatesExtractor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DatabaseConfiguration {
        @Bean
        @ConditionalOnProperty(prefix = "app", name = "database-access-type", havingValue = "jdbc")
        public TgChatService jdbcChatService(JdbcTgChatDao tgChatDao) {
                return new JdbcTgChatService(tgChatDao);
        }

        @Bean
        @ConditionalOnProperty(prefix = "app", name = "database-access-type", havingValue = "jdbc")
        public LinkService jdbcLinkService(
                JdbcLinkDao linkDao,
                JdbcTgChatLinkDao tgChatLinkDao
        ) {
                return new JdbcLinkService(linkDao, tgChatLinkDao);
        }

        @Bean
        @ConditionalOnProperty(prefix = "app", name = "database-access-type", havingValue = "jdbc")
        public LinkUpdater jdbcLinkUpdater(
                JdbcLinkDao linkDao,
                JdbcTgChatLinkDao tgChatLinkDao,
                SendLinkUpdate sendLinkUpdate,
                GithubUpdatesExtractor githubUpdatesExtractor,
                StackOverflowUpdatesExtractor stackOverflowUpdatesExtractor
        ) {
                return new JdbcLinkUpdater(
                        linkDao,
                        tgChatLinkDao,
                        sendLinkUpdate,
                        githubUpdatesExtractor,
                        stackOverflowUpdatesExtractor
                );
        }

        @Bean
        @ConditionalOnProperty(prefix = "app", name = "database-access-type", havingValue = "jpa")
        public TgChatService jpaTgChatService(JpaTgChatRepository tgChatRepository) {
                return new JpaTgChatService(tgChatRepository);
        }

        @Bean
        @ConditionalOnProperty(prefix = "app", name = "database-access-type", havingValue = "jpa")
        public LinkService jpaLinkService(
                JpaLinkRepository linkRepository,
                JpaTgChatRepository tgChatRepository
        ) {
                return new JpaLinkService(linkRepository, tgChatRepository);
        }

        @Bean
        @ConditionalOnProperty(prefix = "app", name = "database-access-type", havingValue = "jpa")
        public LinkUpdater jpaLinkUpdater(
                JpaLinkRepository linkRepository,
                SendLinkUpdate sendLinkUpdate,
                GithubUpdatesExtractor githubUpdatesExtractor,
                StackOverflowUpdatesExtractor stackOverflowUpdatesExtractor
        ) {
                return new JpaLinkUpdater(
                        linkRepository,
                        sendLinkUpdate,
                        githubUpdatesExtractor,
                        stackOverflowUpdatesExtractor
                );
        }
}
