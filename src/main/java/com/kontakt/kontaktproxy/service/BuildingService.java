package com.kontakt.kontaktproxy.service;

import com.kontakt.kontaktproxy.api.model.Building;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class BuildingService {
   private final Dotenv dotenv = Dotenv.load();
    public Building getBuilding(int id) {
        String url = "https://apps.cloud.us.kontakt.io/v2/locations/buildings/{id}";

        RestTemplate template = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Api-Key", dotenv.get("KONTAKT_API_KEY"));

        HttpEntity<Void> httpEntity = new HttpEntity<>(headers);
        ResponseEntity<Building> buildingResponseEntity = template.exchange(url, HttpMethod.GET, httpEntity,
                Building.class, id);
        Building building = buildingResponseEntity.getBody();
        System.out.println(building);

        return building;
    }
}
