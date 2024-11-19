package subway.view;

import subway.exception.ErrorMessage;

public enum StationLineFeature {
    SELECT_ONE("1"),
    SELECT_TWO("2"),
    SELECT_THREE("3"),
    BACK("B");

    final String message;

    StationLineFeature(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return message;
    }

    public static StationLineFeature getFeatureFromInput(String input) {
        for (StationLineFeature feature : StationLineFeature.values()) {
            if (feature.toString().equalsIgnoreCase(input)) {
                return feature;
            }
        }
        throw new IllegalArgumentException(ErrorMessage.INVALID_FEATURE.toString());
    }
}
