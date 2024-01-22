package com.kontakt.kontaktproxy.api.controller;

import com.kontakt.kontaktproxy.api.model.Building;
import com.kontakt.kontaktproxy.component.IpRateLimiter;
import com.kontakt.kontaktproxy.service.BuildingService;
import com.kontakt.kontaktproxy.service.BuildingServiceException;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
public class BuildingControllerTest {

    @Mock
    private BuildingService buildingService;

    @Mock
    private IpRateLimiter ipRateLimiter;

    @Mock
    private HttpServletRequest request;

    @InjectMocks
    private BuildingController buildingController;

    @Test
    public void testGetBuilding_Success() {
        when(ipRateLimiter.allowRequest(any())).thenReturn(true);

        Building mockBuilding = new Building();
        when(buildingService.getBuilding(any())).thenReturn(mockBuilding);

        ResponseEntity<?> responseEntity = buildingController.getBuilding("1", request);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, responseEntity.getHeaders().getContentType());
        assertEquals(mockBuilding, responseEntity.getBody());

        verify(ipRateLimiter, times(1)).allowRequest(any());
    }

    @Test
    public void testGetBuilding_ServiceException() {
        when(ipRateLimiter.allowRequest(any())).thenReturn(true);

        int statusCode = HttpStatus.NOT_FOUND.value();
        String responseBody = "Building not found";
        MediaType contentType = MediaType.APPLICATION_JSON;
        when(buildingService.getBuilding(any())).thenThrow(new BuildingServiceException("Not Found", statusCode, responseBody, contentType));

        ResponseEntity<?> responseEntity = buildingController.getBuilding("1", request);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertEquals(contentType, responseEntity.getHeaders().getContentType());
        assertEquals(responseBody, responseEntity.getBody());

        verify(ipRateLimiter, times(1)).allowRequest(any());
    }
}
