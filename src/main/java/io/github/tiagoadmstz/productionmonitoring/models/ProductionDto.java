package io.github.tiagoadmstz.productionmonitoring.models;

import lombok.Data;

@Data
public class ProductionDto {

    private Long id;
    private Long code;
    private Long minutes = 0L;
    private RegisterType productionType = RegisterType.PRODUCAO;

}
