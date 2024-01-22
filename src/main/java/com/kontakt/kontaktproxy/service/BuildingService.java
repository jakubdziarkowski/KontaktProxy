package com.kontakt.kontaktproxy.service;

import com.kontakt.kontaktproxy.api.model.Building;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

@Service
public class BuildingService {
    private final String url;
    private final RestTemplate template;
    private final HttpEntity<Void> httpEntity;
    @Autowired
    public BuildingService(RestTemplate template) {
        this.template = template;
        Dotenv dotenv = Dotenv.load();
        this.url = "https://apps.cloud.us.kontakt.io/v2/locations/buildings/{id}";

        HttpHeaders headers = new HttpHeaders();
        headers.add("Api-Key", dotenv.get("KONTAKT_API_KEY"));
        httpEntity = new HttpEntity<>(headers);
    }
    public Building getBuilding(String id) {
        try {
            ResponseEntity<Building> buildingResponseEntity = template.exchange(url, HttpMethod.GET, httpEntity,
                    Building.class, id);
            return buildingResponseEntity.getBody();
        } catch (Exception e) {
            int statusCode = -1;
            String responseBody = "";
            MediaType contentType = MediaType.ALL;

            if (e instanceof HttpStatusCodeException httpException) {
                statusCode = httpException.getStatusCode().value();
                responseBody = httpException.getResponseBodyAsString();

                HttpHeaders responseHeaders = httpException.getResponseHeaders();
                contentType = (responseHeaders != null) ? responseHeaders.getContentType() : MediaType.ALL;
            }

            throw new BuildingServiceException(e.getMessage(), statusCode, responseBody, contentType);
        }
    }
}
