package com.kontakt.kontaktproxy.service;

import com.kontakt.kontaktproxy.api.model.Building;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class BuildingService {
    public Building getBuilding(Integer id) {
        Building building = new Building();
        building.setId(id);
        building.setName("Government building");
        building.setAddress("Wiejska 4/6/8, 00-902 Warszawa");
        building.setFloors(new ArrayList<>());

        return building;
    }
}
