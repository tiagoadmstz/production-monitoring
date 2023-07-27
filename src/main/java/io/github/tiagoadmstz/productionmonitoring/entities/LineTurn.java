package io.github.tiagoadmstz.productionmonitoring.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Turno_Linha", schema = "dbo")
public class LineTurn implements Serializable {

    @Id
    private Long id;
    @Column(name = "TURNO")
    private String turn;
    @Column(name = "DESCRICAO")
    private String description;
    @Column(name = "LINHA")
    private String line;
    @Column(name = "VIGENCIA_INICIAL")
    private LocalDateTime initialValidity;
    @Column(name = "VIGENCIA_FINAL")
    private LocalDateTime finalValidity;
    @Column(name = "INICIO_TURNO")
    private LocalDateTime turnStart;
    @Column(name = "FIM_TURNO")
    private LocalDateTime turnEnd;
    @Column(name = "PADRAO")
    private Boolean defaultTurn;

}
