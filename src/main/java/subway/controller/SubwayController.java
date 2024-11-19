package subway.controller;

import java.util.EnumMap;
import subway.constants.MainFeature;
import subway.constants.SectionFeature;
import subway.constants.StationLineFeature;
import subway.domain.Line;
import subway.domain.Station;
import subway.exception.InputValidator;
import subway.repository.LineRepository;
import subway.repository.StationRepository;
import subway.service.LineService;
import subway.service.MainService;
import subway.service.SectionService;
import subway.service.StationService;
import subway.util.FileLoader;
import subway.util.RetryHandler;
import subway.view.InputView;
import subway.view.OutputView;

public class SubwayController {

    private final InputView inputView;
    private final OutputView outputView;
    private final MainService mainService;
    private final StationService stationService;
    private final LineService lineService;
    private final SectionService sectionService;

    private boolean isRunning = true;

    public SubwayController(InputView inputView, OutputView outputView, MainService mainService,
                            StationService stationService, LineService lineService, SectionService sectionService) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.mainService = mainService;
        this.stationService = stationService;
        this.lineService = lineService;
        this.sectionService = sectionService;
    }

    public void start() {
        FileLoader.loadSettings();

        while (isRunning) {
            handledMain(selectMainFeature());
            outputView.printBlank();
        }
    }

    private MainFeature selectMainFeature() {
        outputView.printMain();
        return RetryHandler.handleRetry(() -> {
            String selectedFeature = inputView.askFeature();
            return mainService.selectMainFeature(selectedFeature);
        });
    }

    private void handledMain(MainFeature selectedFeature) {
        EnumMap<MainFeature, Runnable> actions = new EnumMap<>(MainFeature.class);
        actions.put(MainFeature.SELECT_ONE, () -> processStationManagement(selectStationFeature()));
        actions.put(MainFeature.SELECT_TWO, () -> processLineManagement(selectLineFeature()));
        actions.put(MainFeature.SELECT_THREE, () -> processSectionManagement(selectSectionFeature()));
        actions.put(MainFeature.SELECT_FOUR, this::showSubwayLines);
        actions.put(MainFeature.QUIT, this::quitProgram);

        Runnable action = actions.get(selectedFeature);
        executeAction(action);
    }

    private void executeAction(Runnable action) {
        if (action != null) {
            action.run();
        }
    }

    private StationLineFeature selectStationFeature() {
        outputView.printStationManagement();
        return RetryHandler.handleRetry(() -> {
            String selectedFeature = inputView.askFeature();
            return stationService.selectStationFeature(selectedFeature);
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
        executeAction(action);
    }

    private void enrollStation() {
        RetryHandler.handleRetry(() -> {
            String stationName = inputView.askStationEnroll();
            stationService.addStation(stationName);
            outputView.printEnrollStationSuccess();
        });
    }

    private void deleteStation() {
        RetryHandler.handleRetry(() -> {
            String stationName = inputView.askStationDelete();
            stationService.deleteStation(stationName);
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
            return lineService.selectLineFeature(selectedFeature);
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
        executeAction(action);
    }

    private void handleEnrollLine() {
        String lineName = getLEnrollLineName();
        Station upperStation = getUpperStation();
        Station lowerStation = getLowerStation(upperStation);
        enrollLine(lineName, upperStation, lowerStation);
    }

    private void enrollLine(String lineName, Station upperStation, Station lowerStation) {
        lineService.enrollLine(lineName, upperStation, lowerStation);
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
            return lineService.getValidLineName(lineName);
        });
    }

    private Station getUpperStation() {
        return RetryHandler.handleRetry(() -> {
            String upperStation = inputView.askUpperStation();
            return stationService.getStation(upperStation);
        });
    }

    private Station getLowerStation(Station upperStation) {
        return RetryHandler.handleRetry(() -> {
            String lowerStation = inputView.askLowerStation();
            return stationService.getLowerStation(upperStation, lowerStation);
        });
    }

    private String getLDeleteLine() {
        return RetryHandler.handleRetry(() -> {
            String lineName = inputView.askLineDelete();
            return lineService.getValidLineName(lineName);
        });
    }

    private SectionFeature selectSectionFeature() {
        outputView.printSectionManagement();
        return RetryHandler.handleRetry(() -> {
            String selectedFeature = inputView.askFeature();
            return sectionService.selectSectionFeature(selectedFeature);
        });
    }

    private void processSectionManagement(SectionFeature selectedFeature) {
        EnumMap<SectionFeature, Runnable> actions = new EnumMap<>(SectionFeature.class);
        actions.put(SectionFeature.SELECT_ONE, this::enrollSection);
        actions.put(SectionFeature.SELECT_TWO, this::deleteSection);
        actions.put(SectionFeature.BACK, () -> {
        });

        Runnable action = actions.get(selectedFeature);
        executeAction(action);
    }

    private void enrollSection() {
        RetryHandler.handleRetry(() -> {
            Line line = getEnrollLineOfSection();
            Station station = getStationOfSection();
            int order = getOrderOfSection(line);

            LineRepository.addSection(line, station, order);
            outputView.printEnrollSectionSuccess();
        });
    }

    private void deleteSection() {
        RetryHandler.handleRetry(() -> {
            Line line = getDeleteLineOfSection();
            Station station = getDeleteStationOfSection();

            LineRepository.deleteSection(line, station);
            outputView.printDeleteSectionSuccess();
        });
    }

    private Line getEnrollLineOfSection() {
        return RetryHandler.handleRetry(() -> {
            String lineName = inputView.askLineNameOfSection();
            return lineService.getLine(lineName);
        });
    }

    private Station getStationOfSection() {
        return RetryHandler.handleRetry(() -> {
            String stationName = inputView.askStationNameOfSection();
            return stationService.getStation(stationName);
        });
    }

    private int getOrderOfSection(Line line) {
        return RetryHandler.handleRetry(() -> {
            return InputValidator.validateOrder(line, inputView.askOrderOfSection());
        });
    }

    private Line getDeleteLineOfSection() {
        return RetryHandler.handleRetry(() -> {
            String lineName = inputView.askDeleteLineOfSection();
            return lineService.getLine(lineName);
        });
    }

    private Station getDeleteStationOfSection() {
        return RetryHandler.handleRetry(() -> {
            String stationName = inputView.askDeleteStationOfSection();
            return stationService.getStation(stationName);
        });
    }

    private void showSubwayLines() {
        outputView.printSubwayLinesInformation(LineRepository.lines());
    }

    private void quitProgram() {
        isRunning = false;
    }
}
