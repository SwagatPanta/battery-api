package com.vpp.battery_api.controller;

import com.vpp.battery_api.model.Battery;
import com.vpp.battery_api.service.BatteryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/batteries")
@RequiredArgsConstructor
public class BatteryController {

    private final BatteryService batteryService;

    /**
     * POST /api/batteries/save
     * Accepts a list of Battery objects and saves them in the database.
     */
    @PostMapping("/save")
    public ResponseEntity<List<Battery>> saveBatteries(@RequestBody List<Battery> batteries) {
        List<Battery> savedBatteries = batteryService.saveAll(batteries);
        return ResponseEntity.ok(savedBatteries);
    }

    /**
     * GET /api/batteries/range?fromPostcode=6000&toPostcode=7000
     * Optional query params: minCapacity, maxCapacity
     *
     * Returns batteries within the postcode range and optional capacity filters.
     */
    @GetMapping("/range")
    public ResponseEntity<Map<String, Object>> getBatteriesInRange(
            @RequestParam String fromPostcode,
            @RequestParam String toPostcode,
            @RequestParam Optional<Integer> minCapacity,
            @RequestParam Optional<Integer> maxCapacity
    ) {
        Map<String, Object> result = batteryService.getBatteriesInRange(fromPostcode, toPostcode, minCapacity, maxCapacity);
        return ResponseEntity.ok(result);
    }
}
