package subway;

import subway.config.AppConfig;
import subway.controller.SubwayController;
import subway.view.OutputView;

public class Application {
    public static void main(String[] args) {
        try {
            final AppConfig appConfig = AppConfig.INSTANCE;
            final SubwayController subwayController = appConfig.getController();
            subwayController.start();
        } catch (IllegalArgumentException e) {
            OutputView.printError(e.getMessage());
        }
    }
}
