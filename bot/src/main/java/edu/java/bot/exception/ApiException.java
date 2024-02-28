package edu.java.bot.exception;

import lombok.Getter;

@Getter
public class ApiException extends Exception {
    private final int code;
    private final String responseBody;

    public ApiException(String message, int code, String responseBody) {
        super(message);

        this.code = code;
        this.responseBody = responseBody;
    }
}
