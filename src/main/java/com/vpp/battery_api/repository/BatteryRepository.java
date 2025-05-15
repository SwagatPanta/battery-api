package com.vpp.battery_api.repository;

import com.vpp.battery_api.model.Battery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BatteryRepository extends JpaRepository<Battery, Long> {
    List<Battery> findByPostcodeBetween(String from, String to);
}
