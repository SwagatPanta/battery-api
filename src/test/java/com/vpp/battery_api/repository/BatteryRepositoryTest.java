package com.vpp.battery_api.repository;

import com.vpp.battery_api.TestcontainersConfiguration;
import com.vpp.battery_api.model.Battery;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(TestcontainersConfiguration.class)
public class BatteryRepositoryTest {

    @Autowired
    private BatteryRepository batteryRepository;

    @Test
    void testFindByPostcodeBetween() {
        List<Battery> testData = List.of(
                new Battery(null, "Cannington", "6107", 13500),
                new Battery(null, "Midland", "6057", 50500),
                new Battery(null, "Hay Street", "6000", 23500),
                new Battery(null, "Mount Adams", "6525", 12000),
                new Battery(null, "Koolan Island", "6733", 10000),
                new Battery(null, "Armadale", "6992", 25000),
                new Battery(null, "Lesmurdie", "6076", 13500),
                new Battery(null, "Kalamunda", "6076", 13500),
                new Battery(null, "Carmel", "6076", 36000),
                new Battery(null, "Bentley", "6102", 85000),
                new Battery(null, "Akunda Bay", "2084", 13500),
                new Battery(null, "Werrington County", "2747", 13500),
                new Battery(null, "Bagot", "0820", 27000),
                new Battery(null, "Yirrkala", "0880", 13500),
                new Battery(null, "University of Melbourne", "3010", 85000),
                new Battery(null, "Norfolk Island", "2899", 13500),
                new Battery(null, "Ootha", "2875", 13500),
                new Battery(null, "Kent Town", "5067", 13500),
                new Battery(null, "Northgate Mc", "9464", 13500),
                new Battery(null, "Gold Coast Mc", "9729", 50000)
        );

        batteryRepository.saveAll(testData);

        // Example range: 6000 - 6107 inclusive
        List<Battery> found = batteryRepository.findByPostcodeBetween("6000", "6107");

        assertThat(found)
                .extracting(Battery::getName)
                .containsExactlyInAnyOrder("Hay Street", "Midland", "Lesmurdie", "Kalamunda", "Carmel", "Bentley", "Cannington");
    }
}
