package com.vpp.battery_api.service;

import com.vpp.battery_api.model.Battery;
import com.vpp.battery_api.repository.BatteryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class BatteryServiceTest {

    private BatteryRepository repository;
    private BatteryService service;

    @BeforeEach
    void setUp() {
        repository = mock(BatteryRepository.class);
        service = new BatteryService(repository);
    }

    @Test
    void testGetBatteriesInRange_withFullRealTestSubset() {
        List<Battery> testData = List.of(
                new Battery(1L, "Cannington", "6107", 13500),
                new Battery(2L, "Midland", "6057", 50500),
                new Battery(3L, "Hay Street", "6000", 23500),
                new Battery(4L, "Bentley", "6102", 85000)
        );

        when(repository.findByPostcodeBetween("6000", "6107")).thenReturn(testData);

        Map<String, Object> result = service.getBatteriesInRange(
                "6000", "6107",
                Optional.empty(), Optional.empty()
        );

        assertEquals(List.of("Bentley", "Cannington", "Hay Street", "Midland"), result.get("batteries"));
        assertEquals(172500, result.get("totalWattCapacity"));
        assertEquals(43125.0, result.get("averageWattCapacity"));
    }

    @Test
    void testGetBatteriesInRange_withMinCapacityFilter() {
        List<Battery> testData = List.of(
                new Battery(1L, "Cannington", "6107", 13500),
                new Battery(2L, "Midland", "6057", 50500),
                new Battery(3L, "Hay Street", "6000", 23500),
                new Battery(4L, "Bentley", "6102", 85000)
        );

        when(repository.findByPostcodeBetween("6000", "6107")).thenReturn(testData);

        Map<String, Object> result = service.getBatteriesInRange(
                "6000", "6107",
                Optional.of(30000), Optional.empty()
        );

        @SuppressWarnings("unchecked")
        List<String> filteredNames = (List<String>) result.get("batteries");

        assertEquals(List.of("Bentley", "Midland"), filteredNames);
        assertEquals(135500, result.get("totalWattCapacity"));
        assertEquals(67750.0, result.get("averageWattCapacity"));
    }

    @Test
    void testGetBatteriesInRange_withNoMatchesFromFilters() {
        List<Battery> testData = List.of(
                new Battery(1L, "Midland", "6057", 50500),
                new Battery(2L, "Bentley", "6102", 85000)
        );

        when(repository.findByPostcodeBetween("6000", "6107")).thenReturn(testData);

        Map<String, Object> result = service.getBatteriesInRange(
                "6000", "6107",
                Optional.of(100000), Optional.empty()
        );

        @SuppressWarnings("unchecked")
        List<String> batteries = (List<String>) result.get("batteries");

        assertTrue(batteries.isEmpty());
        assertEquals(0, result.get("totalWattCapacity"));
        assertEquals(0.0, result.get("averageWattCapacity"));
    }
}
