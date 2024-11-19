package subway.controller;

import java.util.EnumMap;
import java.util.LinkedList;
import java.util.List;
import subway.domain.Line;
import subway.domain.Station;
import subway.exception.InputValidator;
import subway.repository.LineRepository;
import subway.repository.StationRepository;
import subway.util.FileLoader;
import subway.util.RetryHandler;
import subway.view.InputView;
import subway.view.MainFeature;
import subway.view.OutputView;
import subway.view.StationLineFeature;

public class SubwayController {

    private final InputView inputView;
    private final OutputView outputView;

    private boolean isRunning = true;

    public SubwayController(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void start() {
        FileLoader.loadSettings();

        while (isRunning) {
            handledMain(selectMainFeature());
            System.out.println();
        }
    }

    private MainFeature selectMainFeature() {
        outputView.printMain();
        return RetryHandler.handleRetry(() -> {
            String selectedFeature = inputView.askFeature();
            InputValidator.validateInput(selectedFeature);

            return MainFeature.getFeatureFromInput(selectedFeature);
        });
    }

    private void handledMain(MainFeature selectedFeature) {
        EnumMap<MainFeature, Runnable> actions = new EnumMap<>(MainFeature.class);
        actions.put(MainFeature.SELECT_ONE, () -> processStationManagement(selectStationFeature()));
        actions.put(MainFeature.SELECT_TWO, () -> processLineManagement(selectLineFeature()));
        actions.put(MainFeature.SELECT_FOUR, this::showSubwayLines);
        actions.put(MainFeature.QUIT, this::quitProgram);

        Runnable action = actions.get(selectedFeature);
        if (action != null) {
            action.run();
        }
    }

    private StationLineFeature selectStationFeature() {
        outputView.printStationManagement();
        return RetryHandler.handleRetry(() -> {
            String selectedFeature = inputView.askFeature();
            InputValidator.validateInput(selectedFeature);

            return StationLineFeature.getFeatureFromInput(selectedFeature);
        });
    }

    private void processStationManagement(StationLineFeature selectedFeature) {
        EnumMap<StationLineFeature, Runnable> actions = new EnumMap<>(StationLineFeature.class);
        actions.put(StationLineFeature.SELECT_ONE, this::enrollStation);
        actions.put(StationLineFeature.SELECT_TWO, this::deleteStation);
        actions.put(StationLineFeature.SELECT_THREE, this::showStations);
        actions.put(StationLineFeature.BACK, () -> {
        });

        Runnable action = actions.get(selectedFeature);
        if (action != null) {
            action.run();
        }
    }

    private void enrollStation() {
        RetryHandler.handleRetry(() -> {
            String stationName = inputView.askStationEnroll();
            InputValidator.validateInput(stationName);
            InputValidator.validateNameLength(stationName);

            StationRepository.addStation(new Station(stationName));
            outputView.printEnrollStationSuccess();
        });
    }

    private void deleteStation() {
        RetryHandler.handleRetry(() -> {
            String stationName = inputView.askStationDelete();
            InputValidator.validateInput(stationName);
            InputValidator.validateNameLength(stationName);

            StationRepository.deleteStation(stationName);
            outputView.printDeleteStationSuccess();
        });
    }

    private void showStations() {
        outputView.printStations(StationRepository.stations());
    }

    private StationLineFeature selectLineFeature() {
        outputView.printLineManagement();
        return RetryHandler.handleRetry(() -> {
            String selectedFeature = inputView.askFeature();
            InputValidator.validateInput(selectedFeature);

            return StationLineFeature.getFeatureFromInput(selectedFeature);
        });
    }

    private void processLineManagement(StationLineFeature selectedFeature) {
        EnumMap<StationLineFeature, Runnable> actions = new EnumMap<>(StationLineFeature.class);
        actions.put(StationLineFeature.SELECT_ONE, this::handleEnrollLine);
        actions.put(StationLineFeature.SELECT_TWO, () -> deleteLine(getLDeleteLine()));
        actions.put(StationLineFeature.SELECT_THREE, this::showLines);
        actions.put(StationLineFeature.BACK, () -> {
        });

        Runnable action = actions.get(selectedFeature);
        if (action != null) {
            action.run();
        }
    }

    private void handleEnrollLine() {
        String lineName = getLEnrollLineName();
        Station upperStation = getUpperStation();
        Station lowerStation = getLowerStation(upperStation);
        enrollLine(lineName, upperStation, lowerStation);
    }

    private void enrollLine(String lineName, Station upperStation, Station lowerStation) {
        LinkedList<Station> stations = new LinkedList<>();
        stations.add(upperStation);
        stations.add(lowerStation);

        Line line = new Line(lineName, stations);
        LineRepository.addLine(line);
        outputView.printEnrollLineSuccess();
    }

    private void deleteLine(String lineName) {
        LineRepository.deleteLineByName(lineName);
        outputView.printDeleteLineSuccess();
    }

    private void showLines() {
        outputView.printLines(LineRepository.lines());
    }

    private String getLEnrollLineName() {
        return RetryHandler.handleRetry(() -> {
            String lineName = inputView.askLineEnroll();
            InputValidator.validateInput(lineName);
            InputValidator.validateNameLength(lineName);

            return lineName;
        });
    }

    private Station getUpperStation() {
        return RetryHandler.handleRetry(() -> {
            String upperStation = inputView.askUpperStation();
            InputValidator.validateInput(upperStation);
            InputValidator.validateNameLength(upperStation);
            InputValidator.validateStationExists(upperStation);

            return StationRepository.findStation(upperStation).get();
        });
    }

    private Station getLowerStation(Station upperStation) {
        return RetryHandler.handleRetry(() -> {
            String lowerStation = inputView.askLowerStation();
            InputValidator.validateInput(lowerStation);
            InputValidator.validateNameLength(lowerStation);
            InputValidator.validateStationExists(lowerStation);
            InputValidator.validateDuplicateInput(upperStation, lowerStation);

            return StationRepository.findStation(lowerStation).get();
        });
    }

    private String getLDeleteLine() {
        return RetryHandler.handleRetry(() -> {
            String lineName = inputView.askLineDelete();
            InputValidator.validateInput(lineName);
            InputValidator.validateNameLength(lineName);

            return lineName;
        });
    }


    private void showSubwayLines() {
        List<Line> lines = LineRepository.lines();
        outputView.printSubwayLinesInformation(lines);
    }

    private void quitProgram() {
        isRunning = false;
    }
}
