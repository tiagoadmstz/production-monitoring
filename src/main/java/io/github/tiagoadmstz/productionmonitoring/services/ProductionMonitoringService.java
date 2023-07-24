package io.github.tiagoadmstz.productionmonitoring.services;

import io.github.tiagoadmstz.productionmonitoring.entities.Ddz;
import io.github.tiagoadmstz.productionmonitoring.entities.DdzControl;
import io.github.tiagoadmstz.productionmonitoring.entities.LineTurn;
import io.github.tiagoadmstz.productionmonitoring.handlers.DdzProductionHandler;
import io.github.tiagoadmstz.productionmonitoring.models.DdzProductionDto;
import io.github.tiagoadmstz.productionmonitoring.repositories.DdzControlRepository;
import io.github.tiagoadmstz.productionmonitoring.repositories.LineTurnRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductionMonitoringService {

    private static final Logger LOG = LogManager.getLogger(ProductionMonitoringService.class);
    private final DdzControlRepository ddzControlRepository;
    private final LineTurnRepository lineTurnRepository;
    private Integer ddzOrder = 0;

    public ProductionMonitoringService(DdzControlRepository ddzControlRepository, LineTurnRepository lineTurnRepository) {
        this.ddzControlRepository = ddzControlRepository;
        this.lineTurnRepository = lineTurnRepository;
    }

    public List<DdzProductionDto> getDdzProductionDtoList(final String turn, final int day, final int month, final int year) {
        final Optional<LineTurn> lineTurn = lineTurnRepository.findByTurn(turn);
        if (lineTurn.isPresent()) {
            final List<DdzProductionDto> ddzList = getDdzProductionDtoList(day, month, year);
            return filterByTurn(ddzList, lineTurn.get());
        }
        return Collections.emptyList();
    }

    private List<DdzProductionDto> getDdzProductionDtoList(int day, int month, int year) {
        final List<DdzControl> ddzControlList = ddzControlRepository.findByBaseDate(LocalDate.of(year, month, day));
        return ddzControlList.stream()
                .flatMap(ddzControl -> ddzControl.getDdzList().stream())
                .distinct()
                .map(this::createDdzProductionDto)
                .collect(Collectors.toList());
    }

    private List<DdzProductionDto> filterByTurn(final List<DdzProductionDto> ddzList, final LineTurn lineTurn) {
        //get absolut interval
        List<DdzProductionDto> exactList = ddzList.stream()
                .filter(ddz -> DdzProductionHandler.isBetween(lineTurn, ddz.getPeriod()))
                .collect(Collectors.toList());
        //check if turn starting on correct time
        exactList = DdzProductionHandler.checkStart(ddzList, exactList, exactList.get(0), lineTurn.getTurnStart().toLocalTime());
        //check if turn ending on correct time
        return DdzProductionHandler.checkEnd(ddzList, exactList, exactList.get(exactList.size() - 1), lineTurn.getTurnEnd().toLocalTime());
    }

    private DdzProductionDto createDdzProductionDto(Ddz ddz) {
        return new DdzProductionDto(ddzOrder++, ddz);
    }
}
