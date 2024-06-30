package io.github.tiagoadmstz.productionmonitoring;

import io.github.tiagoadmstz.productionmonitoring.entities.LineTurn;
import io.github.tiagoadmstz.productionmonitoring.handlers.DdzProductionHandler;
import io.github.tiagoadmstz.productionmonitoring.models.DdzProductionDto;
import io.github.tiagoadmstz.productionmonitoring.repositories.LineTurnRepository;
import io.github.tiagoadmstz.productionmonitoring.services.DdzDummyDataService;
import io.github.tiagoadmstz.productionmonitoring.services.ProductionMonitoringService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.annotation.Order;

import java.util.List;
import java.util.Optional;

import static io.github.tiagoadmstz.productionmonitoring.handlers.DdzProductionHandler.calculateSteps;

@SpringBootTest
class ProductionMonitoringApplicationTests {

    @Autowired
    private ProductionMonitoringService productionMonitoringService;
    @Autowired
    private DdzDummyDataService dummyDataService;
    @Autowired
    private LineTurnRepository lineTurnRepository;

    @Test
    void contextLoads() {
    }

    @Test
    @Order(3)
    void testProductionMonitoringHandling() {
        final List<DdzProductionDto> productionDtoList = productionMonitoringService.getDdzProductionDtoList("C", 27, 7, 2023);
        final Optional<LineTurn> lineTurn = lineTurnRepository.findByTurn("C");
        calculateSteps(productionDtoList, lineTurn.orElseGet(LineTurn::new));
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
