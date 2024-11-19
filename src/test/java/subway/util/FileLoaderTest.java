package subway.util;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.LinkedList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import subway.domain.Line;
import subway.domain.Station;
import subway.exception.ErrorMessage;
import subway.repository.LineRepository;
import subway.repository.StationRepository;

class FileLoaderTest {

    private static final String STATION_NAME_1 = "교대역";
    private static final String STATION_NAME_2 = "강남역";
    private static final String STATION_NAME_3 = "역삼역";
    private static final String INVALID_STATION_NAME = "역";
    private static final String LINE_NAME = "2호선";

    @BeforeEach
    void setUp() {
        StationRepository.clearStations();
        LineRepository.clearLines();
    }

    @Test
    @DisplayName("올바른 역 데이터를 읽어와 저장한다.")
    void handleStation() {
        String stationData = STATION_NAME_1 + System.lineSeparator() + STATION_NAME_2
                + System.lineSeparator() + STATION_NAME_3;
        stationData.lines().forEach(FileLoader::handleStation);

        List<Station> stations = StationRepository.stations();
        assertEquals(3, stations.size());
    }

    @Test
    @DisplayName("역 데이터가 null이라면 예외가 발생한다.")
    void handleStationNull() {
        assertThatThrownBy(() -> FileLoader.handleStation(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorMessage.INVALID_INPUT.toString());
    }

    @Test
    @DisplayName("역 데이터가 공백이라면 예외가 발생한다.")
    void handleStationBlank() {
        assertThatThrownBy(() -> FileLoader.handleStation(""))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorMessage.INVALID_INPUT.toString());
    }

    @Test
    @DisplayName("역 데이터가 2글자 미만이라면 예외가 발생한다.")
    void handleStationNameShort() {
        assertThatThrownBy(() -> FileLoader.handleStation(INVALID_STATION_NAME))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorMessage.INVALID_NAME_LENGTH.toString());
    }

    @Test
    @DisplayName("올바른 노선 데이터를 읽어와 저장한다.")
    void handleLine() {
        createStations();

        String lineData = LINE_NAME + "-" + STATION_NAME_1 + "," + STATION_NAME_2 + "," + STATION_NAME_3;
        lineData.lines().forEach(FileLoader::handleLine);

        List<Line> lines = LineRepository.lines();
        assertEquals(1, lines.size());
        assertEquals(LINE_NAME, lines.get(0).getName());
        assertEquals(3, lines.get(0).getStations().size());
    }

    @Test
    @DisplayName("노선 데이터의 역이 존재하지 않으면 예외가 발생한다.")
    void handleLineStationNotExists() {
        assertThatThrownBy(() -> FileLoader.handleLine(LINE_NAME + "-" + STATION_NAME_1 + "," + STATION_NAME_2))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorMessage.STATION_NOT_EXISTS.toString());
    }

    @Test
    @DisplayName("역 배열 데이터를 LinkedList<Station>으로 변환한다.")
    void handleStationsOfLine() {
        createStations();

        String[] stations = {STATION_NAME_1, STATION_NAME_2, STATION_NAME_3};
        LinkedList<Station> result = FileLoader.handleStationsOfLine(stations);

        assertEquals(3, result.size());
        assertEquals(STATION_NAME_1, result.get(0).getName());
        assertEquals(STATION_NAME_2, result.get(1).getName());
        assertEquals(STATION_NAME_3, result.get(2).getName());
    }

    @Test
    @DisplayName("역 배열에 포함된 역이 존재하지 않으면 예외가 발생한다.")
    void handleStationsOfLine_StationNotExists() {
        String[] stations = {"교대역", "강남역"};
        assertThatThrownBy(() -> FileLoader.handleStationsOfLine(stations))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorMessage.STATION_NOT_EXISTS.toString());
    }

    @Test
    @DisplayName("역 배열의 길이가 2 미만이면 예외가 발생한다.")
    void handleStationsOfLine_StationShortage() {
        String[] stations = {STATION_NAME_1};
        assertThatThrownBy(() -> FileLoader.handleStationsOfLine(stations))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorMessage.STATIONS_SHORTAGE.toString());
    }

    private static void createStations() {
        StationRepository.addStation(new Station(STATION_NAME_1));
        StationRepository.addStation(new Station(STATION_NAME_2));
        StationRepository.addStation(new Station(STATION_NAME_3));
    }
}
