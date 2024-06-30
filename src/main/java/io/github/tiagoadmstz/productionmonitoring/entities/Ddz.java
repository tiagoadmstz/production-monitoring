package io.github.tiagoadmstz.productionmonitoring.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
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
@Table(name = "DDZ", schema = "dbo")
public class Ddz implements Serializable, Comparable<Ddz> {

    private static final long serialVersionUID = 5732841631253034093L;

    @Id
    @Column(name = "ID")
    private Long id;
    @Column(name = "ID_CONTROLE")
    private Long ddzControl;
    @Column(name = "HORA_INPUT")
    private LocalDateTime inputHour;
    @Column(name = "OBSERVACAO")
    private String notes;
    @Column(name = "ID_CAUSA_ID")
    private Long causeId;
    @Column(name = "ID_M_MATERIAL")
    private Long materialId;
    @Column(name = "VELOCIDADE")
    private String speed;
    @Column(name = "FLAG")
    private String flag;

    @Override
    public int compareTo(Ddz ddz) {
        if (ddz.getInputHour().equals(inputHour)) {
            return 0;
        } else if (ddz.getInputHour().isBefore(inputHour)) {
            return 1;
        } else {
            return -1;
        }
    }
}
