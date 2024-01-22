package com.kontakt.kontaktproxy.api.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BuildingTest {

    @Test
    public void testBuilding() {
        Building building = new Building();
        building.setId(1);
        building.setName("Sample Building");
        building.setAddress("123 Main St");

        assertEquals(1, building.getId());
        assertEquals("Sample Building", building.getName());
        assertEquals("123 Main St", building.getAddress());
    }
}


