package subway.service;

import subway.exception.InputValidator;
import subway.view.MainFeature;

public class MainService {

    public MainFeature selectMainFeature(String selectedFeature) {
        InputValidator.validateInput(selectedFeature);

        return MainFeature.getFeatureFromInput(selectedFeature);
    }
}
