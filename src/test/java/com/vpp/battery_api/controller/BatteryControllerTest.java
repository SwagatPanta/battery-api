package com.vpp.battery_api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vpp.battery_api.model.Battery;
import com.vpp.battery_api.service.BatteryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class BatteryControllerTest {

    private MockMvc mockMvc;
    private BatteryService batteryService;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        batteryService = mock(BatteryService.class);
        BatteryController controller = new BatteryController(batteryService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void testSaveBatteries() throws Exception {
        List<Battery> input = List.of(new Battery(null, "TestBattery", "6100", 50000));
        List<Battery> saved = List.of(new Battery(1L, "TestBattery", "6100", 50000));

        when(batteryService.saveAll(any())).thenReturn(saved);

        mockMvc.perform(post("/api/batteries/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name", is("TestBattery")))
                .andExpect(jsonPath("$[0].postcode", is("6100")))
                .andExpect(jsonPath("$[0].capacity", is(50000)));
    }

    @Test
    void testGetBatteriesInRange() throws Exception {
        Map<String, Object> mockResponse = Map.of(
                "batteries", List.of("BatteryA", "BatteryB"),
                "totalWattCapacity", 80000,
                "averageWattCapacity", 40000.0
        );

        when(batteryService.getBatteriesInRange(eq("6000"), eq("7000"), eq(Optional.empty()), eq(Optional.empty())))
                .thenReturn(mockResponse);

        mockMvc.perform(get("/api/batteries/range")
                        .param("fromPostcode", "6000")
                        .param("toPostcode", "7000"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.batteries", hasSize(2)))
                .andExpect(jsonPath("$.totalWattCapacity", is(80000)))
                .andExpect(jsonPath("$.averageWattCapacity", is(40000.0)));
    }
}
