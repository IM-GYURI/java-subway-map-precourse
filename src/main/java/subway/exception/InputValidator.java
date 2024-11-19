package subway.exception;

import java.util.List;
import java.util.Objects;
import subway.domain.Line;
import subway.domain.Station;
import subway.repository.LineRepository;
import subway.repository.StationRepository;

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

    public static void validateStationExists(String name) {
        ValidatorBuilder.from(name)
                .validate(value -> StationRepository.findStation(value).isEmpty(), ErrorMessage.STATION_NOT_EXISTS);
    }

    public static void validateLineExists(String name) {
        ValidatorBuilder.from(name)
                .validate(value -> LineRepository.findLine(value).isEmpty(), ErrorMessage.LINE_NOT_EXISTS);
    }

    public static void validateDuplicateInput(Station upperStation, String lowerStationName) {
        ValidatorBuilder.from(lowerStationName)
                .validate(value -> upperStation.getName().equals(value), ErrorMessage.DUPLICATE_STATION_INPUT_FOR_LINE);
    }

    public static int validateOrder(Line line, String input) {
        List<Station> stations = line.getStations();

        return ValidatorBuilder.from(input)
                .validateIsInteger()
                .validateInteger(value -> value < 1 && value > stations.size() - 1, ErrorMessage.INVALID_SECTION_ORDER)
                .getNumericValue();
    }
}
