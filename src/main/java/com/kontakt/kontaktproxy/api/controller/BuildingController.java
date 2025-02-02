package com.kontakt.kontaktproxy.api.controller;

import com.kontakt.kontaktproxy.api.model.Building;
import com.kontakt.kontaktproxy.component.IpRateLimiter;
import com.kontakt.kontaktproxy.service.BuildingService;
import com.kontakt.kontaktproxy.service.BuildingServiceException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BuildingController {
    private IpRateLimiter ipRateLimiter;
    private BuildingService buildingService;
    @Autowired
    public BuildingController(BuildingService buildingService, IpRateLimiter ipRateLimiter) {
        this.buildingService = buildingService;
        this.ipRateLimiter = ipRateLimiter;
    }
    @GetMapping("/building/{id}")
    public ResponseEntity<?> getBuilding(@PathVariable String id, HttpServletRequest request) {
        String ipAddress = request.getRemoteAddr();
        if (ipRateLimiter.allowRequest(ipAddress)) {
            try {
                Building building = buildingService.getBuilding(id);
                return ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(building);
            } catch (BuildingServiceException e) {
                int statusCode = e.getStatusCode();
                String responseBody = e.getResponseBody();
                MediaType contentType = e.getContentType();

                return ResponseEntity.status(statusCode)
                        .contentType(contentType)
                        .body(responseBody);
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body("{\"error\": {\"code\": \"500\", \"message\": \"Internal Server Error\"}}");
            }
        }
        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).build();
    }

}
