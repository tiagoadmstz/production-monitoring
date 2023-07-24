package io.github.tiagoadmstz.productionmonitoring.models;

import lombok.Data;

@Data
public class ProductionDto {

    private Integer id;
    private Integer code;
    private Integer minutes;
    private RegisterType productionType;

}
