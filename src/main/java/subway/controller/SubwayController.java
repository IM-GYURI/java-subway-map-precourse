package subway.controller;

import java.util.EnumMap;
import java.util.List;
import subway.domain.Line;
import subway.repository.LineRepository;
import subway.util.FileLoader;
import subway.util.RetryHandler;
import subway.view.InputView;
import subway.view.MainFeature;
import subway.view.OutputView;

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
            processSelectFeature(getSelectFeature());
        }
    }

    private MainFeature getSelectFeature() {
        outputView.printMain();
        return RetryHandler.getValidInput(() -> {
            String selectedFeature = inputView.askFeature();
            return MainFeature.getFeatureFromInput(selectedFeature);
        });
    }

    private void processSelectFeature(MainFeature selectedFeature) {
        EnumMap<MainFeature, Runnable> actions = new EnumMap<>(MainFeature.class);
        actions.put(MainFeature.SELECT_FOUR, this::showSubwayLines);
        actions.put(MainFeature.QUIT, this::quitProgram);

        Runnable action = actions.get(selectedFeature);
        if (action != null) {
            action.run();
        }
    }

    private void showSubwayLines() {
        List<Line> lines = LineRepository.lines();
        outputView.printSubwayLinesInformation(lines);
    }

    private void quitProgram() {
        isRunning = false;
    }
}
