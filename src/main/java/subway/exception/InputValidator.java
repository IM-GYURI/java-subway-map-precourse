package subway.exception;

public class InputValidator {

    public static void validateInput(String input) {
        ValidatorBuilder.from(input)
                .validate(value -> value == null || value.isBlank(), ErrorMessage.INVALID_INPUT);
    }

    public static void validateNameLength(String input) {
        ValidatorBuilder.from(input)
                .validate(value -> value.length() < 2, ErrorMessage.INVALID_NAME_LENGTH);
    }

    public static void validateStationsIsMoreThanTwo(String[] stations) {
        ValidatorBuilder.from(stations)
                .validate(value -> value.length < 2, ErrorMessage.STATIONS_SHORTAGE);
    }
}
