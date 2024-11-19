package subway.config;

import java.util.Scanner;
import subway.controller.SubwayController;
import subway.service.LineService;
import subway.service.MainService;
import subway.service.SectionService;
import subway.service.StationService;
import subway.view.InputView;
import subway.view.OutputView;

public enum AppConfig {

    INSTANCE;

    private final Scanner scanner = new Scanner(System.in);
    private final InputView inputView = new InputView(scanner);
    private final OutputView outputView = new OutputView();
    private final MainService mainService = new MainService();
    private final StationService stationService = new StationService();
    private final LineService lineService = new LineService();
    private final SectionService sectionService = new SectionService();
    private final SubwayController subwayController = new SubwayController(inputView, outputView, mainService,
            stationService, lineService, sectionService);

    public SubwayController getController() {
        return subwayController;
    }
}
