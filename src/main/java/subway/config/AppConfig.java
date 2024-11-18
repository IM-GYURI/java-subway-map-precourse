package subway.config;

import subway.util.FileLoader;

public class AppConfig {

    private static final AppConfig appConfig = new AppConfig();

    public static AppConfig getAppConfig() {
        setStationsAndLines();
        return appConfig;
    }

    private static void setStationsAndLines() {
        FileLoader.loadSettings();
    }
}
