package edu.java.util;

import java.net.URI;
import java.time.OffsetDateTime;
import java.util.List;

public interface UpdatesExtractor {
    List<Update> getUpdates(URI url, OffsetDateTime timestamp);

    record Update(URI url, String message) {
    }
}
