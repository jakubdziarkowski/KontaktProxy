package com.kontakt.kontaktproxy.api.controller;

import com.kontakt.kontaktproxy.api.model.Building;
import com.kontakt.kontaktproxy.service.BuildingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class BuildingController {

    private BuildingService buildingService;
    @Autowired
    public BuildingController(BuildingService buildingService) {
        this.buildingService = buildingService;
    }
    @GetMapping("/building/{id}")
    public Building getBuilding(@PathVariable int id) {
        return buildingService.getBuilding(id);
    }
}
