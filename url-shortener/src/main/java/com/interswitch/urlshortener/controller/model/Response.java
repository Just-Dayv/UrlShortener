package com.interswitch.urlshortener.controller.model;

import java.util.List;

public class Response  {
    private final String code;
    private final String description;
    private final List<Error> errors;

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public List<Error> getErrors() {
        return errors;
    }

    public Response(String code, String description, List<Error> errors) {
        this.code = code;
        this.description = description;
        this.errors = errors;
    }
}
