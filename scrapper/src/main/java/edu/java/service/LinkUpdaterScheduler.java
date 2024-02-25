package edu.java.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@ConditionalOnProperty(name = "app.scheduler.enable", matchIfMissing = true)
public class LinkUpdaterScheduler {
    private static final Logger LOGGER = LogManager.getLogger();

    @Scheduled(fixedDelayString = "${app.scheduler.interval}")
    public void update() {
        LOGGER.info("Запуск задания по расписанию");
    }
}
