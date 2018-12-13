package com.interswitch.urlshortener.exception;

public class RequestRejectedException extends RuntimeException {

    public RequestRejectedException(String message) {
        super(message);
    }
}
