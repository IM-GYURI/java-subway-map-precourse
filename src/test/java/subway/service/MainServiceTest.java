package subway.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import subway.constants.MainFeature;
import subway.exception.ErrorMessage;

class MainServiceTest {

    private static final String input = "1";

    private MainFeature selectedFeature;
    private MainService mainService;

    @BeforeEach
    void setUp() {
        this.mainService = new MainService();
        selectedFeature = MainFeature.SELECT_ONE;
    }

    @Test
    @DisplayName("선택된 메인 기능을 찾아 반환한다.")
    void selectMainFeature() {
        MainFeature mainFeature = mainService.selectMainFeature(input);

        assertEquals(mainFeature, selectedFeature);
    }

    @Test
    @DisplayName("선택된 메인 기능이 null일 경우 예외가 발생한다.")
    void selectMainFeatureNull() {
        assertThatThrownBy(() -> mainService.selectMainFeature(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorMessage.INVALID_INPUT.toString());
    }

    @Test
    @DisplayName("선택된 메인 기능이 공백일 경우 예외가 발생한다.")
    void selectMainFeatureBlank() {
        assertThatThrownBy(() -> mainService.selectMainFeature(""))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorMessage.INVALID_INPUT.toString());
    }
}