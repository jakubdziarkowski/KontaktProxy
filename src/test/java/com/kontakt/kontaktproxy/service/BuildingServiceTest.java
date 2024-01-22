package com.kontakt.kontaktproxy.service;

import com.kontakt.kontaktproxy.api.model.Building;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BuildingServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private BuildingService buildingService;

    @Test
    public void testGetBuilding_Success() {
        Building mockBuilding = new Building();
        ResponseEntity<Building> mockResponseEntity = new ResponseEntity<>(mockBuilding, HttpStatus.OK);
        when(restTemplate.exchange(any(), any(), any(), eq(Building.class), Optional.ofNullable(any()))).thenReturn(mockResponseEntity);

        Building result = buildingService.getBuilding("1");

        assertNotNull(result);
        assertEquals(mockBuilding, result);

        verify(restTemplate, times(1)).exchange(any(), any(), any(), eq(Building.class), Optional.ofNullable(any()));
    }

    @Test
    public void testGetBuilding_HttpClientErrorException() {
        MediaType contentType = MediaType.TEXT_PLAIN;
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(contentType);
        HttpClientErrorException mockException = new HttpClientErrorException(HttpStatus.NOT_FOUND, "", httpHeaders, "Not Found".getBytes(), null);
        when(restTemplate.exchange(any(), any(), any(), eq(Building.class), Optional.ofNullable(any()))).thenThrow(mockException);

        BuildingServiceException exception = assertThrows(BuildingServiceException.class, () -> buildingService.getBuilding("1"));

        assertEquals(HttpStatus.NOT_FOUND.value(), exception.getStatusCode());
        assertEquals("Not Found", exception.getResponseBody());
        assertEquals(MediaType.TEXT_PLAIN, exception.getContentType());

        verify(restTemplate, times(1)).exchange(any(), any(), any(), eq(Building.class), Optional.ofNullable(any()));
    }
}
