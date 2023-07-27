package io.github.tiagoadmstz.productionmonitoring;

import io.github.tiagoadmstz.productionmonitoring.models.DdzProductionDto;
import io.github.tiagoadmstz.productionmonitoring.services.DdzDummyDataService;
import io.github.tiagoadmstz.productionmonitoring.services.ProductionMonitoringService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.annotation.Order;

import java.util.List;

@SpringBootTest
class ProductionMonitoringApplicationTests {

    @Autowired
    private ProductionMonitoringService productionMonitoringService;
    @Autowired
    private DdzDummyDataService dummyDataService;

    @Test
    void contextLoads() {
    }

    @Test
    @Order(2)
    void testProductionMonitoringService() {
        final List<DdzProductionDto> productionDtoList = productionMonitoringService.getDdzProductionDtoList("C", 27, 7, 2023);
        productionDtoList.forEach(System.out::println);
    }

    @Test
    @Order(1)
    void generateDummyData() {
        dummyDataService.saveDdzDummyData();
    }
}
