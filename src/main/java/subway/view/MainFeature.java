package subway.view;

import subway.exception.ErrorMessage;

public enum MainFeature {
    SELECT_ONE("1"),
    SELECT_TWO("2"),
    SELECT_THREE("3"),
    SELECT_FOUR("4"),
    QUIT("Q");

    final String message;

    MainFeature(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return message;
    }

    public static MainFeature getFeatureFromInput(String input) {
        for (MainFeature feature : MainFeature.values()) {
            if (feature.toString().equalsIgnoreCase(input)) {
                return feature;
            }
        }
        throw new IllegalArgumentException(ErrorMessage.INVALID_FEATURE.toString());
    }
}
