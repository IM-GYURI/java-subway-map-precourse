package subway.service;

import subway.constants.MainFeature;
import subway.exception.InputValidator;

public class MainService {

    public MainFeature selectMainFeature(String selectedFeature) {
        InputValidator.validateInput(selectedFeature);

        return MainFeature.getFeatureFromInput(selectedFeature);
    }
}
