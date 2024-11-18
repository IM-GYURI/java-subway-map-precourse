package subway.repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import subway.domain.Station;
import subway.exception.ErrorMessage;

public class StationRepository {
    private static final List<Station> stations = new ArrayList<>();

    public static List<Station> stations() {
        return Collections.unmodifiableList(stations);
    }

    public static void addStation(Station station) {
        if (isDuplicate(station.getName())) {
            throw new IllegalArgumentException(ErrorMessage.DUPLICATE_STATION_NAME.toString());
        }

        stations.add(station);
    }

    public static void deleteStation(String name) {
        if (findStation(name).isEmpty()) {
            throw new IllegalArgumentException(ErrorMessage.STATION_NOT_EXISTS.toString());
        }

        stations.removeIf(station -> Objects.equals(station.getName(), name));
    }

    public static Optional<Station> findStation(String name) {
        return stations.stream()
                .filter(station -> station.getName().equals(name))
                .findFirst();
    }

    private static boolean isDuplicate(String name) {
        return stations.stream()
                .anyMatch(station -> Objects.equals(station.getName(), name));
    }
}
