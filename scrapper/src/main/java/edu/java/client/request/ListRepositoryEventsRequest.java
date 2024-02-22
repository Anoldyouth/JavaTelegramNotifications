package edu.java.client.request;

import lombok.Data;

@Data
@SuppressWarnings("MagicNumber")
public class ListRepositoryEventsRequest {
    private int perPage = 30;
    private int page = 1;
}
