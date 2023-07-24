package io.github.tiagoadmstz.productionmonitoring.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "DDZ_Controle", schema = "dbo")
public class DdzControl implements Serializable {

    private static final long serialVersionUID = 2622404523450196852L;

    @Id
    @Column(name = "ID")
    private Long id;
    @Column(name = "LINHA")
    private String line;
    @Column(name = "DATA_BASE")
    private LocalDateTime baseDate;
    @OneToMany(mappedBy = "ddzControl", cascade = CascadeType.ALL)
    private List<Ddz> ddzList = new ArrayList<>(1);

}
