package com.vpp.battery_api;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.utility.DockerImageName;

@TestConfiguration(proxyBeanMethods = false)
public class TestcontainersConfiguration {

	private static final MySQLContainer<?> mysqlContainer =
			new MySQLContainer<>(DockerImageName.parse("mysql:8.0.33"))
					.withDatabaseName("testdb")
					.withUsername("testuser")
					.withPassword("testpass");

	static {
		mysqlContainer.start();
	}

	@Bean
	@ServiceConnection
	public MySQLContainer<?> mysqlContainer() {
		return mysqlContainer;
	}
}
