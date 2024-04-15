package edu.java.bot.service;

import edu.java.bot.configuration.properties.BucketConfig;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Service;

@Service
public class RateLimiterService {
    private static final int TOKEN_COUNT = 1;
    private final BucketConfig config;
    private final Map<String, Bucket> ipBucketMap = new ConcurrentHashMap<>();

    public RateLimiterService(BucketConfig config) {
        this.config = config;
    }

    public boolean tryConsume(String ip) {
        Bucket bucket = ipBucketMap.computeIfAbsent(ip, k -> createNewBucket());
        return bucket.tryConsume(TOKEN_COUNT);
    }

    private Bucket createNewBucket() {
        Bandwidth limit = Bandwidth.classic(config.capacity(), Refill.intervally(config.tokens(), config.period()));

        return Bucket.builder()
                .addLimit(limit)
                .build();
    }
}
