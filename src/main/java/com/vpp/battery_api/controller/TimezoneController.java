package com.vpp.battery_api.controller;

import com.vpp.battery_api.service.TimezoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class TimezoneController {
    @Autowired
    private TimezoneService timezoneService;
    @GetMapping("/timezone")
    public Map<String,String> getTimezone(){
        return timezoneService.getCurrentTimezone();
    }
}
