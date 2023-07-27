package io.github.tiagoadmstz.productionmonitoring.services;

import io.github.tiagoadmstz.productionmonitoring.entities.Ddz;
import io.github.tiagoadmstz.productionmonitoring.entities.DdzControl;
import io.github.tiagoadmstz.productionmonitoring.entities.LineTurn;
import io.github.tiagoadmstz.productionmonitoring.repositories.DdzControlRepository;
import io.github.tiagoadmstz.productionmonitoring.repositories.LineTurnRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import javax.persistence.EntityManager;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class DdzDummyDataService {

    private static final Logger LOG = LogManager.getLogger(DdzDummyDataService.class);
    private final EntityManager entityManager;
    private final LineTurnRepository lineTurnRepository;
    private final DdzControlRepository ddzControlRepository;

    public DdzDummyDataService(EntityManager entityManager, LineTurnRepository lineTurnRepository, DdzControlRepository ddzControlRepository) {
        this.entityManager = entityManager;
        this.lineTurnRepository = lineTurnRepository;
        this.ddzControlRepository = ddzControlRepository;
    }

    public void saveDdzDummyData() {
        //createDboSchema();
        saveLineTurnList();
        saveDdzList();
    }

    private void createDboSchema() {
        try {
            entityManager.createNativeQuery("create schema dbo").executeUpdate();
            entityManager.flush();
        } catch (Exception exception) {
            LOG.error("Error on trying create dummy data schema", exception);
        }
    }

    private void saveLineTurnList() {
        if (!lineTurnRepository.findById(1L).isPresent()) {
            final List<LineTurn> lineTurnList = new ArrayList<>();
            lineTurnList.add(LineTurn.builder()
                .id(1L).line("L07").turn("A")
                .description("Turno A")
                .initialValidity(LocalDateTime.now())
                .finalValidity(LocalDateTime.now())
                .turnStart(LocalDateTime.of(LocalDate.now(), LocalTime.of(23, 46)))
                .turnEnd(LocalDateTime.of(LocalDate.now(), LocalTime.of(6, 14)))
                .defaultTurn(true)
                .build()
            );
            lineTurnList.add(LineTurn.builder()
                .id(1L).line("L07").turn("B")
                .description("Turno B")
                .initialValidity(LocalDateTime.now())
                .finalValidity(LocalDateTime.now())
                .turnStart(LocalDateTime.of(LocalDate.now(), LocalTime.of(6, 15)))
                .turnEnd(LocalDateTime.of(LocalDate.now(), LocalTime.of(15, 00)))
                .defaultTurn(true)
                .build()
            );
            lineTurnList.add(LineTurn.builder()
                .id(1L).line("L07").turn("C")
                .description("Turno C")
                .initialValidity(LocalDateTime.now())
                .finalValidity(LocalDateTime.now())
                .turnStart(LocalDateTime.of(LocalDate.now(), LocalTime.of(15, 1)))
                .turnEnd(LocalDateTime.of(LocalDate.now(), LocalTime.of(23, 45)))
                .defaultTurn(true)
                .build()
            );
            lineTurnRepository.saveAll(lineTurnList);
        }
    }

    private void saveDdzList() {
        try {
            if (!ddzControlRepository.findById(10236L).isPresent()) {
                final List<Ddz> ddzList = getDdzList();
                final List<DdzControl> ddzControlList = ddzList.stream().map(Ddz::getDdzControl).distinct()
                        .map(idDdzControl -> new DdzControl(idDdzControl, "L07", LocalDateTime.now(), new ArrayList<>()))
                        .collect(Collectors.toList());
                ddzControlList.forEach(dc ->
                        ddzList.stream().filter(ddz -> ddz.getDdzControl().equals(dc.getId())).forEach(dc.getDdzList()::add)
                );
                ddzControlRepository.saveAll(ddzControlList);
            }
        } catch (IOException ioException) {
            LOG.error("Error on trying save DDZ dummy data", ioException);
        }
    }

    private List<Ddz> getDdzList() throws IOException {
        final List<Ddz> ddzList = new ArrayList<>();
        final List<String> allLines = Files.readAllLines(ResourceUtils.getFile("src/main/resources/dummy-data.csv").toPath());
        IntStream.range(1, allLines.size()).boxed().forEach(index -> {
            final String[] line = allLines.get(index).split(";");
            final Ddz ddz = Ddz.builder()
                    .id(Long.parseLong(line[0]))
                    .ddzControl(Long.parseLong(line[1]))
                    .inputHour(LocalDateTime.parse(line[2], DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")))
                    .notes(line[3])
                    .causeId(Long.parseLong(line[4]))
                    .materialId(Long.parseLong(line[5]))
                    .speed(line[6])
                    .flag(line[7])
                    .build();
            ddzList.add(ddz);
        });
        return ddzList;
    }
}
