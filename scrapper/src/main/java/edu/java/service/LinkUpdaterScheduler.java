package edu.java.service;

import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@ConditionalOnProperty(name = "app.scheduler.enabled", matchIfMissing = true)
@RequiredArgsConstructor
public class LinkUpdaterScheduler {
    private static final Logger LOGGER = LogManager.getLogger();

    private final LinkUpdater linkUpdater;

    @Scheduled(fixedDelayString = "${app.scheduler.interval}")
    public void update() {
        LOGGER.info("LinkUpdaterScheduler стартовал");

        int count = linkUpdater.update();

        LOGGER.info("Проверено ссылок: " + count);
    }
}
