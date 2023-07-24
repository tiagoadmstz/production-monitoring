package io.github.tiagoadmstz.productionmonitoring.repositories;

import io.github.tiagoadmstz.productionmonitoring.entities.LineTurn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LineTurnRepository extends JpaRepository<LineTurn, Long> {

    Optional<LineTurn> findByTurn(String turn);

}
