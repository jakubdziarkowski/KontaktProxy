package com.kontakt.kontaktproxy.api.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FloorTest {

    @Test
    public void testFloor() {
        Floor floor = new Floor();
        floor.setId(1);
        floor.setLevel(2);
        floor.setName("Sample Floor");

        assertEquals(1, floor.getId());
        assertEquals(2, floor.getLevel());
        assertEquals("Sample Floor", floor.getName());
    }
}
