package subway.repository;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import subway.domain.Station;
import subway.exception.ErrorMessage;

class StationRepositoryTest {

    private static final String STATION_NAME = "교대역";
    private static final String DIFFERENT_STATION_NAME = "강남역";

    @BeforeEach
    void setUp() {
        StationRepository.clearStations();
    }

    @Test
    @DisplayName("새로운 역을 추가하면 정상적으로 저장된다.")
    void addStation() {
        addStationOnce();
        List<Station> stations = StationRepository.stations();

        assertEquals(1, stations.size());
    }

    @Test
    @DisplayName("중복된 이름의 역을 추가하면 예외가 발생한다.")
    void addDuplicateStation() {
        addStationOnce();
        assertThatThrownBy(this::addStationOnce)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorMessage.DUPLICATE_STATION_NAME.toString());
    }

    @Test
    @DisplayName("역 이름을 통해 해당 역을 삭제한다.")
    void deleteLineByName() {
        addStationOnce();
        StationRepository.deleteStation(STATION_NAME);

        assertEquals(0, StationRepository.stations().size());
    }

    @Test
    @DisplayName("존재하지 않는 역을 삭제하면 예외가 발생한다.")
    void deleteLineNotExists() {
        addStationOnce();

        assertThatThrownBy(() -> StationRepository.deleteStation(DIFFERENT_STATION_NAME))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorMessage.STATION_NOT_EXISTS.toString());
    }

    @Test
    @DisplayName("역 이름을 통해 역을 찾을 수 있다.")
    void findStation() {
        addStationOnce();
        Optional<Station> foundStation = StationRepository.findStation(STATION_NAME);

        assertTrue(foundStation.isPresent());
        assertEquals(STATION_NAME, foundStation.get().getName());
    }

    @Test
    @DisplayName("존재하지 않는 역을 찾으면 빈 Optional을 반환한다.")
    void findNonExistentStation() {
        addStationOnce();
        Optional<Station> foundStation = StationRepository.findStation(DIFFERENT_STATION_NAME);

        assertTrue(foundStation.isEmpty());
    }

    private void addStationOnce() {
        Station station = new Station(STATION_NAME);
        StationRepository.addStation(station);
    }
}