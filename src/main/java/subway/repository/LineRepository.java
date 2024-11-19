package subway.repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import subway.domain.Line;
import subway.domain.Station;
import subway.exception.ErrorMessage;

public class LineRepository {
    private static final List<Line> lines = new ArrayList<>();

    public static List<Line> lines() {
        return Collections.unmodifiableList(lines);
    }

    public static void addLine(Line line) {
        if (isDuplicate(line.getName())) {
            throw new IllegalArgumentException(ErrorMessage.DUPLICATE_LINE_NAME.toString());
        }

        lines.add(line);
    }

    public static void deleteLineByName(String name) {
        if (findLine(name).isEmpty()) {
            throw new IllegalArgumentException(ErrorMessage.LINE_NOT_EXISTS.toString());
        }

        lines.removeIf(line -> Objects.equals(line.getName(), name));
    }

    public static void addStation(Line line, Station station, int order) {
        if (containsStation(line, station)) {
            throw new IllegalArgumentException(ErrorMessage.LINE_ALREADY_HAS_THIS_STATION.toString());
        }

        LinkedList<Station> stations = new LinkedList<>(line.getStations());
        stations.add(order - 1, station);

        deleteLineByName(line.getName());

        Line newLine = new Line(line.getName(), stations);
        lines.add(newLine);
    }

    public static void deleteSection(Line line, Station station) {
        if (!containsStation(line, station)) {
            throw new IllegalArgumentException(ErrorMessage.LINE_ALREADY_HAS_THIS_STATION.toString());
        }

        LinkedList<Station> stations = new LinkedList<>(line.getStations());

        if (stations.size() < 3) {
            throw new IllegalArgumentException(ErrorMessage.STATIONS_SHORTAGE.toString());
        }

        stations.remove(station);

        deleteLineByName(line.getName());

        Line newLine = new Line(line.getName(), stations);
        lines.add(newLine);
    }

    public static Optional<Line> findLine(String name) {
        return lines.stream()
                .filter(line -> line.getName().equals(name))
                .findFirst();
    }

    public static boolean containsStation(Line line, Station station) {
        return line.getStations()
                .contains(station);
    }

    private static boolean isDuplicate(String name) {
        return lines.stream()
                .anyMatch(station -> Objects.equals(station.getName(), name));
    }
}
