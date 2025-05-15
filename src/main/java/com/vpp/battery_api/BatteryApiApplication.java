package com.vpp.battery_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

//@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@SpringBootApplication
public class BatteryApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(BatteryApiApplication.class, args);
	}

}
