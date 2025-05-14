package com.vpp.battery_api.service;

import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
@Service
public class TimezoneService {
    public Map<String,String> getCurrentTimezone(){
        Map<String,String> response = new HashMap<>();
        ZonedDateTime now =ZonedDateTime.now(ZoneId.systemDefault());
        String formattedTime=now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss z"));
        response.put("timezone",ZoneId.systemDefault().toString());
        response.put("currentTime",formattedTime);
        return response;
    }
}
