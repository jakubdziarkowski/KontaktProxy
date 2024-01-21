package com.kontakt.kontaktproxy.service;

import org.springframework.http.MediaType;

public class BuildingServiceException extends RuntimeException {
    private final int statusCode;
    private final String responseBody;
    private final MediaType contentType;

    public BuildingServiceException(String message, int statusCode, String responseBody, MediaType contentType) {
        super(message);
        this.statusCode = statusCode;
        this.responseBody = responseBody;
        this.contentType = contentType;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getResponseBody() {
        return responseBody;
    }

    public MediaType getContentType() {
        return contentType;
    }
}
