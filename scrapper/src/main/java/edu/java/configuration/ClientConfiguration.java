package edu.java.configuration;

import edu.java.client.github.GitHubClient;
import edu.java.client.stackoverflow.StackOverflowClient;
import edu.java.configuration.properties.GitHubConfig;
import edu.java.configuration.properties.StackOverflowConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class ClientConfiguration {
    private final GitHubConfig gitHubConfig;
    private final StackOverflowConfig stackOverflowConfig;

    @Bean
    public GitHubClient gitHubClient() {
        return new GitHubClient(gitHubConfig);
    }

    @Bean
    public StackOverflowClient stackOverflowClient() {
        return new StackOverflowClient(stackOverflowConfig);
    }
}
