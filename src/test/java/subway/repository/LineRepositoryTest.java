package subway.repository;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.LinkedList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import subway.domain.Line;
import subway.domain.Station;
import subway.exception.ErrorMessage;

class LineRepositoryTest {

    private static final String LINE_NAME = "1호선";
    private static final String DIFFERENT_LINE_NAME = "2호선";
    private static final int STATION_COUNT = 3;
    private static final int SECTION_ORDER = 2;

    private List<Station> stations;

    @BeforeEach
    void setUp() {
        LineRepository.clearLines();
        stations = createTestStations();
    }

    @Test
    @DisplayName("새로운 노선을 추가하면 정상적으로 저장된다.")
    void addLine() {
        addLineOnce();
        assertEquals(1, LineRepository.lines().size());
    }

    @Test
    @DisplayName("중복된 이름의 노선을 추가하면 예외가 발생한다.")
    void addDuplicateLine() {
        addLineOnce();
        assertThatThrownBy(this::addLineOnce)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorMessage.DUPLICATE_LINE_NAME.toString());
    }

    @Test
    @DisplayName("노션 이름을 통해 해당 노선을 삭제한다.")
    void deleteLineByName() {
        addLineOnce();
        LineRepository.deleteLineByName(LINE_NAME);

        assertEquals(0, LineRepository.lines().size());
    }

    @Test
    @DisplayName("존재하지 않는 노선을 삭제하면 예외가 발생한다.")
    void deleteLineNotExists() {
        addLineOnce();

        assertThatThrownBy(() -> LineRepository.deleteLineByName(DIFFERENT_LINE_NAME))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorMessage.LINE_NOT_EXISTS.toString());
    }

    @Test
    @DisplayName("노선에 구간을 추가하면 올바른 순서로 저장된다.")
    void addSection() {
        Line line = addLineOnce();
        List<Station> stationsOfLine = line.getStations();

        LineRepository.addSection(line, stations.get(STATION_COUNT), SECTION_ORDER);
        Line updatedLine = getLine(LINE_NAME);
        int indexOfNewStation = updatedLine.getStations()
                .indexOf(stations.get(STATION_COUNT));

        assertEquals(STATION_COUNT + 1, updatedLine.getStations().size());
        assertEquals(SECTION_ORDER - 1, indexOfNewStation);
    }

    @Test
    @DisplayName("이미 노선에 존재하는 역을 구간 추가하는 경우 예외가 발생한다.")
    void addSectionAlreadyExists() {
        Line line = addLineOnce();
        Station existingStation = stations.get(0);

        assertThatThrownBy(() -> LineRepository.addSection(line, existingStation, SECTION_ORDER))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorMessage.LINE_ALREADY_HAS_THIS_STATION.toString());
    }

    @Test
    @DisplayName("노선에서 구간을 삭제하면 해당 역이 삭제된다.")
    void deleteSection() {
        Line line = addLineOnce();
        Line newLine = deleteStationOnce(line);

        assertFalse(LineRepository.containsStation(newLine, stations.get(0)));
    }

    @Test
    @DisplayName("노선에 존재하지 않는 역을 구간 삭제하면 예외가 발생한다.")
    void deleteSectionNotExistsInLine() {
        Line line = addLineOnce();

        assertThatThrownBy(() -> LineRepository.deleteSection(line, stations.get(STATION_COUNT)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorMessage.LINE_DOES_NOT_HAVE_THIS_STATION.toString());
    }

    @Test
    @DisplayName("노선에서 해당 구간을 삭제할 경우 역의 개수가 2 미만이라면 예외가 발생한다.")
    void deleteSectionLessThanTwo() {
        Line line = addLineOnce();
        Line newLine = deleteStationOnce(line);

        assertThatThrownBy(() -> LineRepository.deleteSection(newLine, stations.get(STATION_COUNT - 1)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorMessage.STATIONS_SHORTAGE.toString());
    }

    private List<Station> createTestStations() {
        return List.of(
                new Station("교대역"),
                new Station("강남역"),
                new Station("역삼역"),
                new Station("남부터미널역"),
                new Station("양재역")
        );
    }

    private Line getLine(String name) {
        return LineRepository.findLine(name)
                .orElseThrow(() -> new IllegalArgumentException(ErrorMessage.LINE_NOT_EXISTS.toString()));
    }

    private Line addLineOnce() {
        LinkedList<Station> currentStations = getStations();

        Line line = new Line(LINE_NAME, currentStations);
        LineRepository.addLine(line);
        return line;
    }

    private LinkedList<Station> getStations() {
        LinkedList<Station> currentStations = new LinkedList<>();

        for (int i = 0; i < STATION_COUNT; i++) {
            currentStations.add(stations.get(i));
        }
        return currentStations;
    }

    private Line deleteStationOnce(Line line) {
        LineRepository.deleteSection(line, stations.get(0));

        return LineRepository.findLine(LINE_NAME)
                .orElseThrow();
    }
}