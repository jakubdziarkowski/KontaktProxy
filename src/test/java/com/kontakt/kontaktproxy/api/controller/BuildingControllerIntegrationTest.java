package com.kontakt.kontaktproxy.api.controller;

import com.kontakt.kontaktproxy.api.model.Building;
import com.kontakt.kontaktproxy.service.BuildingService;
import com.kontakt.kontaktproxy.service.BuildingServiceException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
public class BuildingControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BuildingService buildingService;

    @Test
    public void testGetBuilding() throws Exception {
        Building building = new Building();
        when(buildingService.getBuilding("1")).thenReturn(building);

        mockMvc.perform(MockMvcRequestBuilders.get("/building/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void testGetBuildingWithServiceError() throws Exception {
        when(buildingService.getBuilding("2"))
                .thenThrow(new BuildingServiceException("Service error", 500, "Error body", MediaType.APPLICATION_JSON));

        mockMvc.perform(MockMvcRequestBuilders.get("/building/2")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isInternalServerError())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }
}
