package io.github.tiagoadmstz.productionmonitoring.models;

import lombok.Data;

import java.util.List;

@Data
public class ProductionTurnDto {

    private List<ProductionDto> productionList;
    private Integer totalProduction;
    private Integer totalStops;
    private Integer totalUnscheduledStops;
    private Integer totalTurn;

}
