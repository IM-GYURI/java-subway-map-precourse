package subway.service;

import subway.constants.SectionFeature;
import subway.exception.InputValidator;

public class SectionService {

    public SectionFeature selectSectionFeature(String selectedFeature) {
        InputValidator.validateInput(selectedFeature);

        return SectionFeature.getFeatureFromInput(selectedFeature);
    }
}
