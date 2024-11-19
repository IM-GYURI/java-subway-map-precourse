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

class LineServiceTest {

    private static final String input = "1";
    private static final String VALID_LINE_NAME = "1호선";
    private static final String DIFFERENT_VALID_LINE_NAME = "2호선";
    private static final String INVALID_LINE_NAME = "선";

    private StationLineFeature selectedFeature;
    private LineService lineService;

    @BeforeEach
    void setUp() {
        lineService = new LineService();
        selectedFeature = StationLineFeature.SELECT_ONE;
        LineRepository.clearLines();
    }

    @Test
    @DisplayName("선택된 노선 기능을 찾아 반환한다.")
    void selectLineFeature() {
        StationLineFeature lineFeature = lineService.selectLineFeature(input);
        assertEquals(selectedFeature, lineFeature);
    }

    @Test
    @DisplayName("선택된 노선 기능이 null일 경우 예외가 발생한다.")
    void selectLineFeatureNull() {
        assertThatThrownBy(() -> lineService.selectLineFeature(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorMessage.INVALID_INPUT.toString());
    }

    @Test
    @DisplayName("선택된 노선 기능이 공백일 경우 예외가 발생한다.")
    void selectLineFeatureBlank() {
        assertThatThrownBy(() -> lineService.selectLineFeature(" "))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorMessage.INVALID_INPUT.toString());
    }

    @Test
    @DisplayName("노선 이름을 검증한 후 반환한다.")
    void getValidLineName() {
        String lineName = lineService.getValidLineName(VALID_LINE_NAME);
        assertEquals(VALID_LINE_NAME, lineName);
    }

    @Test
    @DisplayName("노선 이름이 null일 경우 예외가 발생한다.")
    void getValidLineNameNull() {
        assertThatThrownBy(() -> lineService.getValidLineName(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorMessage.INVALID_INPUT.toString());
    }

    @Test
    @DisplayName("노선 이름이 공백일 경우 예외가 발생한다.")
    void getValidLineNameBlank() {
        assertThatThrownBy(() -> lineService.getValidLineName("   "))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorMessage.INVALID_INPUT.toString());
    }

    @Test
    @DisplayName("노선 이름이 2글자 미만일 경우 예외가 발생한다.")
    void getValidLineNameShort() {
        assertThatThrownBy(() -> lineService.getValidLineName(INVALID_LINE_NAME))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorMessage.INVALID_NAME_LENGTH.toString());
    }

    @Test
    @DisplayName("노선 이름으로 노선을 찾아 반환한다.")
    void getLine() {
        Line line = addLine();
        Line findLine = lineService.getLine(VALID_LINE_NAME);

        assertEquals(line, findLine);
    }

    @Test
    @DisplayName("노선 이름이 null일 경우 예외가 발생한다.")
    void getLineNull() {
        assertThatThrownBy(() -> lineService.getLine(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorMessage.INVALID_INPUT.toString());
    }

    @Test
    @DisplayName("노선 이름이 공백일 경우 예외가 발생한다.")
    void getLineBlank() {
        assertThatThrownBy(() -> lineService.getLine("   "))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorMessage.INVALID_INPUT.toString());
    }

    @Test
    @DisplayName("존재하지 않는 노선일 경우 예외가 발생한다.")
    void getLineNotExists() {
        assertThatThrownBy(() -> lineService.getLine(DIFFERENT_VALID_LINE_NAME))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorMessage.LINE_NOT_EXISTS.toString());
    }

    @Test
    @DisplayName("노선 이름과 상행 종점역, 하행 종점역을 통해 노선을 추가한다.")
    void enrollLine() {
        Station upperStation = new Station("교대역");
        Station lowerStation = new Station("강남역");

        lineService.enrollLine(VALID_LINE_NAME, upperStation, lowerStation);

        assertEquals(LineRepository.lines().size(), 1);
    }

    private Line addLine() {
        Line line = new Line(VALID_LINE_NAME, createTestStations());
        LineRepository.addLine(line);

        return LineRepository.findLine(VALID_LINE_NAME)
                .orElseThrow();
    }

    private LinkedList<Station> createTestStations() {
        LinkedList<Station> stations = new LinkedList<>();

        stations.add(new Station("교대역"));
        stations.add(new Station("강남역"));
        stations.add(new Station("역삼역"));

        return stations;
    }
}