package subway.repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import subway.domain.Line;
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

    public static Optional<Line> findLine(String name) {
        return lines.stream()
                .filter(line -> line.getName().equals(name))
                .findFirst();
    }

    private static boolean isDuplicate(String name) {
        return lines.stream()
                .anyMatch(station -> Objects.equals(station.getName(), name));
    }
}
