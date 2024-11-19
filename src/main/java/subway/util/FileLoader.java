package subway.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Optional;
import subway.domain.Line;
import subway.domain.Station;
import subway.exception.ErrorMessage;
import subway.exception.InputValidator;
import subway.repository.LineRepository;
import subway.repository.StationRepository;

public class FileLoader {

    private static final String STATION_FILEPATH = "src/main/resources/stations.md";
    private static final String LINE_FILEPATH = "src/main/resources/lines.md";
    private static final String LINE_SPLITTER = "-";
    private static final String STATION_SPLITTER = ",";
    private static final int LINE_INDEX = 0;
    private static final int STATIONS_INDEX = 1;

    public static void loadSettings() {
        loadStations();
        loadLines();
    }

    private static void loadStations() {
        try (BufferedReader br = new BufferedReader(new FileReader(STATION_FILEPATH))) {
            br.lines()
                    .forEach(FileLoader::handleStation);
        } catch (Exception e) {
            throw new IllegalArgumentException(ErrorMessage.INVALID_FILE_LOAD.toString());
        }
    }

    static void handleStation(String input) {
        InputValidator.validateInput(input);
        InputValidator.validateNameLength(input);

        Station station = new Station(input);
        StationRepository.addStation(station);
    }

    private static void loadLines() {
        try (BufferedReader br = new BufferedReader(new FileReader(LINE_FILEPATH))) {
            br.lines()
                    .forEach(FileLoader::handleLine);
        } catch (Exception e) {
            throw new IllegalArgumentException(ErrorMessage.INVALID_FILE_LOAD.toString());
        }
    }

    static void handleLine(String input) {
        InputValidator.validateInput(input);

        String[] parts = input.split(LINE_SPLITTER);
        String lineName = parts[LINE_INDEX];
        String[] stations = parts[STATIONS_INDEX].split(STATION_SPLITTER);

        InputValidator.validateNameLength(lineName);
        Arrays.stream(stations)
                .forEach(station -> {
                    InputValidator.validateInput(station);
                    InputValidator.validateNameLength(station);
                });

        Line line = new Line(lineName, handleStationsOfLine(stations));
        LineRepository.addLine(line);
    }

    static LinkedList<Station> handleStationsOfLine(String[] stations) {
        InputValidator.validateStationsIsLessThanTwo(stations);
        LinkedList<Station> stationsOfLine = new LinkedList<>();

        Arrays.stream(stations)
                .forEach(stationName -> {
                    Optional<Station> station = StationRepository.findStation(stationName);

                    if (station.isEmpty()) {
                        throw new IllegalArgumentException(ErrorMessage.STATION_NOT_EXISTS.toString());
                    }

                    stationsOfLine.add(station.get());
                });

        return stationsOfLine;
    }
}
