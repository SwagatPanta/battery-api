package com.vpp.battery_api.service;

import com.vpp.battery_api.model.Battery;
import com.vpp.battery_api.repository.BatteryRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class BatteryService {

    private static final Logger log = LoggerFactory.getLogger(BatteryService.class);
    private final BatteryRepository repository;

    public List<Battery> saveAll(List<Battery> batteries) {
        log.info("Saving {} batteries to the database.", batteries.size());
        List<Battery> saved = repository.saveAll(batteries);
        log.debug("Saved batteries: {}", saved);
        return saved;
    }

    public Map<String, Object> getBatteriesInRange(String from, String to, Optional<Integer> minCapacity, Optional<Integer> maxCapacity) {
        log.info("Querying batteries from postcode {} to {}", from, to);
        minCapacity.ifPresent(min -> log.info("Minimum capacity filter: {}", min));
        maxCapacity.ifPresent(max -> log.info("Maximum capacity filter: {}", max));

        List<Battery> filtered = repository.findByPostcodeBetween(from, to)
                .stream()
                .filter(b -> minCapacity.map(min -> b.getCapacity() >= min).orElse(true))
                .filter(b -> maxCapacity.map(max -> b.getCapacity() <= max).orElse(true))
                .sorted(Comparator.comparing(Battery::getName))
                .toList();

        int total = filtered.stream().mapToInt(Battery::getCapacity).sum();
        double avg = filtered.isEmpty() ? 0 : total / (double) filtered.size();

        log.info("Found {} batteries. Total capacity = {}, Average capacity = {}", filtered.size(), total, avg);

        Map<String, Object> res = new HashMap<>();
        res.put("batteries", filtered.stream().map(Battery::getName).toList());
        res.put("totalWattCapacity", total);
        res.put("averageWattCapacity", avg);
        return res;
    }
}
