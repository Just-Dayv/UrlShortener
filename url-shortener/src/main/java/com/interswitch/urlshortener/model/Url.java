package com.interswitch.urlshortener.model;


public class Url {

    private String shortUrlValue;

    private String ID;

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    private String longUrlValue;

    public String getShortUrlValue() {
        return shortUrlValue;
    }

    public void setShortUrlValue(String shortUrlValue) {
        this.shortUrlValue = shortUrlValue;
    }

    public String getLongUrlValue() {
        return longUrlValue;
    }

    public void setLongUrlValue(String longUrlValue) {
        this.longUrlValue = longUrlValue;
    }
}
