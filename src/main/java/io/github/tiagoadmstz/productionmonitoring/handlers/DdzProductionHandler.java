package io.github.tiagoadmstz.productionmonitoring.handlers;

import io.github.tiagoadmstz.productionmonitoring.entities.LineTurn;
import io.github.tiagoadmstz.productionmonitoring.models.DdzProductionDto;

import java.time.LocalTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class DdzProductionHandler {

    public static void calculateSteps(final List<DdzProductionDto> ddzList, final LineTurn lineTurn) {
        final List<DdzProductionDto> ddzProductionDtos = DdzProductionHandler.filterByTurn(ddzList, lineTurn);

    }

    public static List<DdzProductionDto> filterByTurn(final List<DdzProductionDto> ddzList, final LineTurn lineTurn) {
        //get absolut interval
        List<DdzProductionDto> exactList = ddzList.stream()
                .filter(ddz -> DdzProductionHandler.isBetween(lineTurn, ddz.getPeriod()))
                .collect(Collectors.toList());
        //check if turn starting on correct time
        final DdzProductionDto first = exactList.stream().min(Comparator.comparing(DdzProductionDto::getPeriod)).get();
        exactList = DdzProductionHandler.checkStart(ddzList, exactList, first, lineTurn.getTurnStart().toLocalTime());
        //check if turn ending on correct time
        final DdzProductionDto last = exactList.stream().max(Comparator.comparing(DdzProductionDto::getPeriod)).get();
        return DdzProductionHandler.checkEnd(ddzList, exactList, last, lineTurn.getTurnEnd().toLocalTime());
    }

    public static List<DdzProductionDto> checkStart(final List<DdzProductionDto> completeDdzList, final List<DdzProductionDto> ddzList, final DdzProductionDto ddzProduction, final LocalTime turnStart) {
        if (completeDdzList.stream().noneMatch(ddzProductionDto -> ddzProductionDto.getPeriod().equals(turnStart))) {
            final Optional<DdzProductionDto> extraDdz = completeDdzList.stream()
                    .filter(ddzProductionDto -> ddzProductionDto.getOrder() == (ddzProduction.getOrder() - 1))
                    .peek(ddzProductionDto -> ddzProductionDto.setPeriod(turnStart))
                    .findFirst();
            extraDdz.ifPresent(ddzList::add);
            return ddzList.stream().sorted().collect(Collectors.toList());
        }
        return ddzList;
    }

    public static List<DdzProductionDto> checkEnd(final List<DdzProductionDto> completeDdzList, final List<DdzProductionDto> ddzList, final DdzProductionDto ddzProduction, final LocalTime turnEnd) {
        if (completeDdzList.stream().noneMatch(ddzProductionDto -> ddzProductionDto.getPeriod().equals(turnEnd))) {
            final Optional<DdzProductionDto> extraDdz = completeDdzList.stream()
                    .filter(ddzProductionDto -> ddzProductionDto.getOrder() == (ddzProduction.getOrder() + 1))
                    .peek(ddzProductionDto -> ddzProductionDto.setPeriod(turnEnd))
                    .findFirst();
            extraDdz.ifPresent(ddzList::add);
            return ddzList.stream().sorted().collect(Collectors.toList());
        }
        return ddzList;
    }

    public static boolean isBetween(LineTurn lineTurn, LocalTime inputTime) {
        final int start = inputTime.compareTo(lineTurn.getTurnStart().toLocalTime());
        final int end = inputTime.compareTo(lineTurn.getTurnEnd().toLocalTime());
        return start >= 0 && end <= 0;
    }
}
