package com.interswitch.urlshortener.controller.model;

import java.util.List;

public class UrlResponse extends Response {
    public String getRedirectUrl() {
        return redirectUrl;
    }

    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }

    String redirectUrl;

    public UrlResponse(String code, String description, List<Error> errors, String returnUrl) {
        super(code, description, errors);
        this.redirectUrl = returnUrl;
    }

}
