package io.github.tiagoadmstz.productionmonitoring.models;

import io.github.tiagoadmstz.productionmonitoring.entities.Ddz;
import lombok.Data;

import java.time.LocalTime;

@Data
public class DdzProductionDto implements Comparable<DdzProductionDto> {

    private Integer order;
    private LocalTime period;
    private Integer speed;
    private Long stopCode;
    private RegisterType registerType;
    private String notes;

    public DdzProductionDto(Integer order, Ddz ddz) {
        this.order = order;
        this.period = ddz.getInputHour().toLocalTime();
        this.speed = Integer.getInteger(ddz.getSpeed());
        this.stopCode = ddz.getCauseId();
        this.notes = ddz.getNotes();
        this.registerType = flagClassification(ddz.getFlag());
    }

    private RegisterType flagClassification(String flag) {
        switch (flag) {
            case "P":
                return RegisterType.PARADA;
            case "E":
            case "L":
            default:
                return RegisterType.PRODUCAO;
        }
    }

    @Override
    public int compareTo(DdzProductionDto ddzProductionDto) {
        return order.compareTo(ddzProductionDto.getOrder());
    }
}
