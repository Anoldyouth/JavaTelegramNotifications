package edu.java.configuration;

import edu.java.client.GitHubClient;
import edu.java.client.StackOverflowClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ClientConfiguration {
    @Autowired GitHubConfig gitHubConfig;
    @Autowired StackOverflowConfig stackOverflowConfig;

    @Bean
    public GitHubClient gitHubClient() {
        return new GitHubClient(gitHubConfig);
    }

    @Bean
    public StackOverflowClient stackOverflowClient() {
        return new StackOverflowClient(stackOverflowConfig);
    }
}
