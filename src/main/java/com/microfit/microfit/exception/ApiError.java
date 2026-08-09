package com.microfit.microfit.exception;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Consistent error response body returned by {@link GlobalExceptionHandler}
 * for every failure case, so API consumers always get the same shape back.
 */
public class ApiError {

    private LocalDateTime timestamp;
    private int status;
    private String error;
    private String message;
    private List<String> details;

    public ApiError() {
    }

    public ApiError(int status, String error, String message, List<String> details) {
        this.timestamp = LocalDateTime.now();
        this.status = status;
        this.error = error;
        this.message = message;
        this.details = details;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public int getStatus() {
        return status;
    }

    public String getError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    public List<String> getDetails() {
        return details;
    }
}
