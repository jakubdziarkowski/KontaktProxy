package com.kontakt.kontaktproxy.service;

import com.kontakt.kontaktproxy.api.model.Building;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

@Service
public class BuildingService {
    private final String url;
    private final RestTemplate template;
    private final HttpEntity<Void> httpEntity;
    public BuildingService() {
       Dotenv dotenv = Dotenv.load();
       this.url = "https://apps.cloud.us.kontakt.io/v2/locations/buildings/{id}";
       this.template = new RestTemplate();
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
            String contentType = "";

            if (e instanceof HttpStatusCodeException httpException) {
                statusCode = httpException.getStatusCode().value();
                responseBody = httpException.getResponseBodyAsString();

                HttpHeaders responseHeaders = httpException.getResponseHeaders();
                contentType = (responseHeaders != null) ? responseHeaders.getFirst(HttpHeaders.CONTENT_TYPE) : "";
            }

            throw new BuildingServiceException(e.getMessage(), statusCode, responseBody, contentType);
        }
    }
}
