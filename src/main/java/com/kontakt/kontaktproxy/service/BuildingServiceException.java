package com.kontakt.kontaktproxy.service;

public class BuildingServiceException extends RuntimeException {
    private final int statusCode;
    private final String responseBody;
    private final String contentType;

    public BuildingServiceException(String message, int statusCode, String responseBody, String contentType) {
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

    public String getContentType() {
        return contentType;
    }
}
