package subway.controller;

import java.util.EnumMap;
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
import subway.view.StationFeature;

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
        actions.put(MainFeature.SELECT_FOUR, this::showSubwayLines);
        actions.put(MainFeature.QUIT, this::quitProgram);

        Runnable action = actions.get(selectedFeature);
        if (action != null) {
            action.run();
        }
    }

    private StationFeature selectStationFeature() {
        outputView.printStationManagement();
        return RetryHandler.handleRetry(() -> {
            String selectedFeature = inputView.askFeature();
            InputValidator.validateInput(selectedFeature);

            return StationFeature.getFeatureFromInput(selectedFeature);
        });
    }

    private void processStationManagement(StationFeature selectedFeature) {
        EnumMap<StationFeature, Runnable> actions = new EnumMap<>(StationFeature.class);
        actions.put(StationFeature.SELECT_ONE, this::enrollStation);
        actions.put(StationFeature.SELECT_TWO, this::deleteStation);
        actions.put(StationFeature.SELECT_THREE, this::showStations);
        actions.put(StationFeature.BACK, () -> {
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
        outputView.printStations();
    }

    private void showSubwayLines() {
        List<Line> lines = LineRepository.lines();
        outputView.printSubwayLinesInformation(lines);
    }

    private void quitProgram() {
        isRunning = false;
    }
}
