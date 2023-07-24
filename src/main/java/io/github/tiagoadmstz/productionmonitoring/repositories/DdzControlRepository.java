package io.github.tiagoadmstz.productionmonitoring.repositories;

import io.github.tiagoadmstz.productionmonitoring.entities.DdzControl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface DdzControlRepository extends JpaRepository<DdzControl, Long> {

    @Query("from DdzControl a JOIN FETCH a.ddzList where FORMATDATETIME(a.baseDate, 'YYYY-MM-dd') = :baseDate order by a.baseDate")
    List<DdzControl> findByBaseDate(LocalDate baseDate);

}
