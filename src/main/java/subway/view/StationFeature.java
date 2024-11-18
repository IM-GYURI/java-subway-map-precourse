package subway.view;

import subway.exception.ErrorMessage;

public enum StationFeature {
    SELECT_ONE("1"),
    SELECT_TWO("2"),
    SELECT_THREE("3"),
    BACK("B");

    final String message;

    StationFeature(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return message;
    }

    public static StationFeature getFeatureFromInput(String input) {
        for (StationFeature feature : StationFeature.values()) {
            if (feature.toString().equalsIgnoreCase(input)) {
                return feature;
            }
        }
        throw new IllegalArgumentException(ErrorMessage.INVALID_FEATURE.toString());
    }
}
