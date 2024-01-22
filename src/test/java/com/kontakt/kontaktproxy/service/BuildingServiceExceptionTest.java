package com.kontakt.kontaktproxy.service;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class BuildingServiceExceptionTest {

    @Test
    public void testBuildingServiceExceptionProperties() {
        String message = "Test Message";
        int statusCode = 404;
        String responseBody = "Not Found";
        MediaType contentType = MediaType.APPLICATION_JSON;

        BuildingServiceException exception = new BuildingServiceException(message, statusCode, responseBody, contentType);

        assertEquals(message, exception.getMessage());
        assertEquals(statusCode, exception.getStatusCode());
        assertEquals(responseBody, exception.getResponseBody());
        assertEquals(contentType, exception.getContentType());
    }

    @Test
    public void testBuildingServiceExceptionWithoutResponseBodyAndContentType() {
        String message = "Test Message";
        int statusCode = 500;

        BuildingServiceException exception = new BuildingServiceException(message, statusCode, null, null);

        assertEquals(message, exception.getMessage());
        assertEquals(statusCode, exception.getStatusCode());
        assertNull(exception.getResponseBody());
        assertNull(exception.getContentType());
    }
}
