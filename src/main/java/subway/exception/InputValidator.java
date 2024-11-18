package subway.exception;

import java.util.Objects;

public class InputValidator {

    public static void validateInput(String input) {
        ValidatorBuilder.from(input)
                .validate(Objects::isNull, ErrorMessage.INVALID_INPUT)
                .validate(String::isBlank, ErrorMessage.INVALID_INPUT);
    }

    public static void validateNameLength(String input) {
        ValidatorBuilder.from(input)
                .validate(value -> value.length() < 2, ErrorMessage.INVALID_NAME_LENGTH);
    }

    public static void validateStationsIsLessThanTwo(String[] stations) {
        ValidatorBuilder.from(stations)
                .validate(value -> value.length < 2, ErrorMessage.STATIONS_SHORTAGE);
    }
}
