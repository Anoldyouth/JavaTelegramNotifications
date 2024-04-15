package edu.java.bot.configuration;

import edu.java.bot.configuration.properties.RetryConfig;
import edu.java.bot.util.LinearRetryBackoffSpec;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.util.retry.Retry;

@Configuration
@EnableRetry
@RequiredArgsConstructor
public class RetryBackoffSpecConfiguration {
    private final RetryConfig retryConfig;

    @Bean
    @ConditionalOnProperty(prefix = "app.retry", name = "policy", havingValue = "fixed")
    Retry fixedRetry() {
        return Retry.fixedDelay(
                retryConfig.maxAttempts(),
                retryConfig.delay()
        ).filter(this::errorFilter);
    }

    @Bean
    @ConditionalOnProperty(prefix = "app.retry", name = "policy", havingValue = "linear")
    Retry linearRetry() {
        return LinearRetryBackoffSpec.linearDelay(
                retryConfig.maxAttempts(),
                retryConfig.delay(),
                retryConfig.interval()
        ).filter(this::errorFilter);
    }

    @Bean
    @ConditionalOnProperty(prefix = "app.retry", name = "policy", havingValue = "exponential")
    Retry exponentialRetry() {
        return Retry.backoff(
                retryConfig.maxAttempts(),
                retryConfig.delay()
        ).filter(this::errorFilter);
    }

    private boolean errorFilter(Throwable throwable) {
        return throwable instanceof WebClientResponseException
                && retryConfig.codes().contains(((WebClientResponseException) throwable).getStatusCode().value());
    }
}
