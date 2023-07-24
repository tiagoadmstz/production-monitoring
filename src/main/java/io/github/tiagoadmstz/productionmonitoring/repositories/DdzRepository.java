package io.github.tiagoadmstz.productionmonitoring.repositories;

import io.github.tiagoadmstz.productionmonitoring.entities.Ddz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DdzRepository extends JpaRepository<Ddz, Long> {
}
