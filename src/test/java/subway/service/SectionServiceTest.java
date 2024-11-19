package subway.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import subway.constants.SectionFeature;
import subway.exception.ErrorMessage;

class SectionServiceTest {

    private static final String input = "1";

    private SectionFeature selectedFeature;
    private SectionService sectionService;

    @BeforeEach
    void setUp() {
        this.sectionService = new SectionService();
        selectedFeature = SectionFeature.SELECT_ONE;
    }

    @Test
    @DisplayName("선택된 구간 기능을 찾아 반환한다.")
    void selectMainFeature() {
        SectionFeature sectionFeature = sectionService.selectSectionFeature(input);

        assertEquals(sectionFeature, selectedFeature);
    }

    @Test
    @DisplayName("선택된 구간 기능이 null일 경우 예외가 발생한다.")
    void selectMainFeatureNull() {
        assertThatThrownBy(() -> sectionService.selectSectionFeature(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorMessage.INVALID_INPUT.toString());
    }

    @Test
    @DisplayName("선택된 구간 기능이 공백일 경우 예외가 발생한다.")
    void selectMainFeatureBlank() {
        assertThatThrownBy(() -> sectionService.selectSectionFeature(""))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorMessage.INVALID_INPUT.toString());
    }
}