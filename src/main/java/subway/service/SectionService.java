package subway.service;

import subway.exception.InputValidator;
import subway.view.SectionFeature;

public class SectionService {

    public SectionFeature selectSectionFeature(String selectedFeature) {
        InputValidator.validateInput(selectedFeature);

        return SectionFeature.getFeatureFromInput(selectedFeature);
    }
}
