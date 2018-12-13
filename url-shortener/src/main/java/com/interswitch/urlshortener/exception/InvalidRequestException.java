package com.interswitch.urlshortener.exception;

public class InvalidRequestException extends RuntimeException {


    public InvalidRequestException(String message) {
        super(message);
    }
}
