package subway.constants;

import subway.exception.ErrorMessage;

public enum SectionFeature {
    SELECT_ONE("1"),
    SELECT_TWO("2"),
    BACK("B");

    final String message;

    SectionFeature(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return message;
    }

    public static SectionFeature getFeatureFromInput(String input) {
        for (SectionFeature feature : SectionFeature.values()) {
            if (feature.toString().equalsIgnoreCase(input)) {
                return feature;
            }
        }
        throw new IllegalArgumentException(ErrorMessage.INVALID_FEATURE.toString());
    }
}
