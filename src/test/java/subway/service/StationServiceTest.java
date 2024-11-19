package subway.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.LinkedList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import subway.constants.StationLineFeature;
import subway.domain.Line;
import subway.domain.Station;
import subway.exception.ErrorMessage;
import subway.repository.LineRepository;
import subway.repository.StationRepository;

class StationServiceTest {

    private static final String input = "1";
    private static final String STATION_NAME = "교대역";
    private static final String DIFFERENT_STATION_NAME = "강남역";
    private static final String INVALID_STATION_NAME = "역";

    private StationLineFeature selectedFeature;
    private StationService stationService;

    @BeforeEach
    void setUp() {
        this.stationService = new StationService();
        selectedFeature = StationLineFeature.SELECT_ONE;
        StationRepository.clearStations();
    }

    @Test
    @DisplayName("선택된 역 기능을 찾아 반환한다.")
    void selectMainFeature() {
        StationLineFeature stationFeature = stationService.selectStationFeature(input);

        assertEquals(stationFeature, selectedFeature);
    }

    @Test
    @DisplayName("선택된 역 기능이 null일 경우 예외가 발생한다.")
    void selectMainFeatureNull() {
        assertThatThrownBy(() -> stationService.selectStationFeature(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorMessage.INVALID_INPUT.toString());
    }

    @Test
    @DisplayName("선택된 역 기능이 공백일 경우 예외가 발생한다.")
    void selectMainFeatureBlank() {
        assertThatThrownBy(() -> stationService.selectStationFeature(""))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorMessage.INVALID_INPUT.toString());
    }

    @Test
    @DisplayName("역 이름을 통해 새로운 역을 추가한다.")
    void addStation() {
        stationService.addStation(STATION_NAME);

        assertEquals(1, StationRepository.stations().size());
    }

    @Test
    @DisplayName("역 이름이 null일 경우 추가 시 예외가 발생한다.")
    void addStationNull() {
        assertThatThrownBy(() -> stationService.addStation(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorMessage.INVALID_INPUT.toString());
    }

    @Test
    @DisplayName("역 이름이 공백일 경우 추가 시 예외가 발생한다.")
    void addStationBlank() {
        assertThatThrownBy(() -> stationService.addStation("  "))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorMessage.INVALID_INPUT.toString());
    }

    @Test
    @DisplayName("역 이름이 2글자 미만일 경우 추가 시 예외가 발생한다.")
    void addStationNameShort() {
        assertThatThrownBy(() -> stationService.addStation(INVALID_STATION_NAME))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorMessage.INVALID_NAME_LENGTH.toString());
    }

    @Test
    @DisplayName("역 이름을 통해 역을 삭제한다.")
    void deleteStation() {
        addStationOnce();
        stationService.deleteStation(STATION_NAME);

        assertEquals(0, StationRepository.stations().size());
    }

    @Test
    @DisplayName("역 이름이 null일 경우 삭제 시 예외가 발생한다.")
    void deleteStationNull() {
        assertThatThrownBy(() -> stationService.deleteStation(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorMessage.INVALID_INPUT.toString());
    }

    @Test
    @DisplayName("역 이름이 공백일 경우 삭제 시 예외가 발생한다.")
    void deleteStationBlank() {
        assertThatThrownBy(() -> stationService.deleteStation(" "))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorMessage.INVALID_INPUT.toString());
    }

    @Test
    @DisplayName("역 이름이 2글자 미만일 경우 삭제 시 예외가 발생한다.")
    void deleteStationNameShort() {
        assertThatThrownBy(() -> stationService.deleteStation(INVALID_STATION_NAME))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorMessage.INVALID_NAME_LENGTH.toString());
    }

    @Test
    @DisplayName("해당 역이 노선에 등록되어 있는 경우 예외가 발생한다.")
    void deleteStationAlreadyInLine() {
        addStationToLine();

        assertThatThrownBy(() -> stationService.deleteStation(STATION_NAME))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorMessage.ALREADY_IN_LINE.toString());
    }

    @Test
    @DisplayName("역 이름으로 역을 찾아 반환한다.")
    void getStation() {
        Station station = addStationOnce();
        Station findStation = stationService.getStation(STATION_NAME);

        assertEquals(station, findStation);
    }

    @Test
    @DisplayName("찾는 역 이름이 null일 경우 예외가 발생한다.")
    void getStationNull() {
        addStationOnce();

        assertThatThrownBy(() -> stationService.getStation(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorMessage.INVALID_INPUT.toString());
    }

    @Test
    @DisplayName("찾는 역 이름이 공백일 경우 예외가 발생한다.")
    void getStationBlank() {
        addStationOnce();

        assertThatThrownBy(() -> stationService.getStation(""))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorMessage.INVALID_INPUT.toString());
    }

    @Test
    @DisplayName("찾는 역 이름이 2글자 미만일 경우 예외가 발생한다.")
    void getStationNameShort() {
        addStationOnce();

        assertThatThrownBy(() -> stationService.getStation(INVALID_STATION_NAME))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorMessage.INVALID_NAME_LENGTH.toString());
    }

    @Test
    @DisplayName("존재하지 않는 역 이름일 경우 예외가 발생한다.")
    void getStationNotExists() {
        addStationOnce();

        assertThatThrownBy(() -> stationService.getStation(DIFFERENT_STATION_NAME))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorMessage.STATION_NOT_EXISTS.toString());
    }

    @Test
    @DisplayName("하행 종점역을 찾아 반환한다.")
    void getLowerStation() {
        Station upperStation = addStationOnce();
        StationRepository.addStation(new Station(DIFFERENT_STATION_NAME));
        Station findStation = stationService.getLowerStation(upperStation, DIFFERENT_STATION_NAME);

        assertEquals(findStation, StationRepository.findStation(DIFFERENT_STATION_NAME).get());
    }

    @Test
    @DisplayName("하행 종점역 이름이 null일 경우 예외가 발생한다.")
    void getLowerStationNull() {
        Station upperStation = addStationOnce();

        assertThatThrownBy(() -> stationService.getLowerStation(upperStation, null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorMessage.INVALID_INPUT.toString());
    }

    @Test
    @DisplayName("하행 종점역 이름이 공백일 경우 예외가 발생한다.")
    void getLowerStationBlank() {
        Station upperStation = addStationOnce();

        assertThatThrownBy(() -> stationService.getLowerStation(upperStation, " "))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorMessage.INVALID_INPUT.toString());
    }

    @Test
    @DisplayName("하행 종점역 이름이 2글자 미만일 경우 예외가 발생한다.")
    void getLowerStationNameShort() {
        Station upperStation = addStationOnce();

        assertThatThrownBy(() -> stationService.getLowerStation(upperStation, INVALID_STATION_NAME))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorMessage.INVALID_NAME_LENGTH.toString());
    }

    @Test
    @DisplayName("하행 종점역이 존재하지 않을 경우 예외가 발생한다.")
    void getLowerStationNotExists() {
        Station upperStation = addStationOnce();

        assertThatThrownBy(() -> stationService.getLowerStation(upperStation, DIFFERENT_STATION_NAME))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorMessage.STATION_NOT_EXISTS.toString());
    }

    @Test
    @DisplayName("하행 종점역이 상행 종점역과 중복될 경우 예외가 발생한다.")
    void getLowerStationDuplicate() {
        Station upperStation = addStationOnce();

        assertThatThrownBy(() -> stationService.getLowerStation(upperStation, STATION_NAME))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorMessage.DUPLICATE_STATION_INPUT_FOR_LINE.toString());
    }

    private void addStationToLine() {
        Station station = addStationOnce();
        LinkedList<Station> stations = new LinkedList<>();
        stations.add(station);

        Line line = new Line("1호선", stations);
        LineRepository.addLine(line);
    }

    private Station addStationOnce() {
        StationRepository.addStation(new Station(STATION_NAME));

        return StationRepository.findStation(STATION_NAME)
                .orElseThrow();
    }
}