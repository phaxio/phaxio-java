package com.phaxio.exceptions;

public class PhaxioClientException extends RuntimeException {
    public PhaxioClientException(String message) {
        super(message);
    }
    public PhaxioClientException(String message, Throwable cause) {
        super(message, cause);
    }
}
