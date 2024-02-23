package edu.java.bot.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class ChainMapper {
    @Autowired
    @Qualifier("mainChain")
    private Chain mainChain;

    @Autowired
    @Qualifier("trackUrlChain")
    private Chain trackUrlChain;

    @Autowired
    @Qualifier("untrackUrlChain")
    private Chain untrackUrlChain;

    public enum ChainType {
        MAIN,
        TRACK_URL,
        UNTRACK_URL
    }

    public Chain getChain(ChainType type) {
        return switch (type) {
            case MAIN -> mainChain;
            case TRACK_URL -> trackUrlChain;
            case UNTRACK_URL -> untrackUrlChain;
        };
    }
}
