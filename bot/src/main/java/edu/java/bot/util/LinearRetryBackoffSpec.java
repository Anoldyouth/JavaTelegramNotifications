package edu.java.bot.util;

import java.time.Duration;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Predicate;
import org.reactivestreams.Publisher;
import reactor.core.Exceptions;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.util.retry.Retry;

public class LinearRetryBackoffSpec extends Retry {
    @SuppressWarnings("LineLength")
    static final BiFunction<LinearRetryBackoffSpec, RetrySignal, Throwable> BACKOFF_EXCEPTION_GENERATOR = (builder, rs) -> Exceptions.retryExhausted("Retries exhausted: " + rs.totalRetries() + "/" + builder.maxAttempts, rs.failure());

    public final Duration minBackoff;
    public final Duration maxBackoff = Duration.ofMillis(Long.MAX_VALUE);
    public final long maxAttempts;
    public final Duration interval;
    public final Predicate<? super Throwable> errorFilter;
    public final BiFunction<LinearRetryBackoffSpec, RetrySignal, Throwable> retryExhaustedGenerator;

    LinearRetryBackoffSpec(
            Duration minBackoff,
            long maxAttempts,
            Duration interval,
            Predicate<? super Throwable> errorFilter,
            BiFunction<LinearRetryBackoffSpec, Retry.RetrySignal, Throwable> retryExhaustedGenerator
    ) {
        this.minBackoff = minBackoff;
        this.maxAttempts = maxAttempts;
        this.interval = interval;
        this.errorFilter = errorFilter;
        this.retryExhaustedGenerator = retryExhaustedGenerator;
    }

    public LinearRetryBackoffSpec filter(Predicate<? super Throwable> errorFilter) {
        return new LinearRetryBackoffSpec(
                this.minBackoff,
                this.maxAttempts,
                this.interval,
                Objects.requireNonNull(errorFilter, "errorFilter"),
                this.retryExhaustedGenerator
        );
    }

    public static LinearRetryBackoffSpec linearDelay(
            long maxAttempts,
            Duration delay,
            Duration interval
    ) {
        return new LinearRetryBackoffSpec(delay, maxAttempts, interval, null, BACKOFF_EXCEPTION_GENERATOR);
    }

    @Override
    public Publisher<?> generateCompanion(Flux<RetrySignal> retrySignals) {
        return Flux.deferContextual((cv) -> retrySignals.contextWrite(cv).concatMap((retryWhenState) -> {
            RetrySignal copy = retryWhenState.copy();
            Throwable currentFailure = copy.failure();
            long iteration = copy.totalRetries();

            if (currentFailure == null) {
                return Mono.error(new IllegalStateException("Retry.RetrySignal#failure() not expected to be null"));
            }

            if (!this.errorFilter.test(currentFailure)) {
                return Mono.error(currentFailure);
            }

            if (iteration >= this.maxAttempts) {
                return Mono.error(this.retryExhaustedGenerator.apply(this, copy));
            }

            Duration nextBackoff;
            try {
                nextBackoff = this.minBackoff.plus(interval.multipliedBy(iteration));
                if (nextBackoff.compareTo(this.maxBackoff) > 0) {
                    nextBackoff = this.maxBackoff;
                }
            } catch (ArithmeticException e) {
                nextBackoff = this.maxBackoff;
            }

            return Mono.delay(nextBackoff, Schedulers.parallel()).contextWrite(cv);
        }).onErrorStop());
    }
}
