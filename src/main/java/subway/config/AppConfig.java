package subway.config;

import java.util.Scanner;
import subway.controller.SubwayController;
import subway.view.InputView;
import subway.view.OutputView;

public enum AppConfig {

    INSTANCE;

    private final Scanner scanner = new Scanner(System.in);
    private final InputView inputView = new InputView(scanner);
    private final OutputView outputView = new OutputView();
    private final SubwayController subwayController = new SubwayController(inputView, outputView);

    public SubwayController getController() {
        return subwayController;
    }
}
