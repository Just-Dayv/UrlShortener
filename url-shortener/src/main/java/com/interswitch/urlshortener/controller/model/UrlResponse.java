package com.interswitch.urlshortener.controller.model;

import java.util.List;

public class UrlResponse extends Response {
    public String getReturnUrl() {
        return returnUrl;
    }

    public void setReturnUrl(String returnUrl) {
        this.returnUrl = returnUrl;
    }

    String returnUrl;

    public UrlResponse(String code, String description, List<Error> errors, String returnUrl) {
        super(code, description, errors);
        this.returnUrl = returnUrl;
    }

}
