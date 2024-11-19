package subway.service;

import subway.domain.Station;
import subway.exception.InputValidator;
import subway.repository.StationRepository;
import subway.view.StationLineFeature;

public class StationService {

    public StationLineFeature selectStationFeature(String selectedFeature) {
        InputValidator.validateInput(selectedFeature);

        return StationLineFeature.getFeatureFromInput(selectedFeature);
    }

    public void addStation(String stationName) {
        InputValidator.validateInput(stationName);
        InputValidator.validateNameLength(stationName);

        StationRepository.addStation(new Station(stationName));
    }

    public void deleteStation(String stationName) {
        InputValidator.validateInput(stationName);
        InputValidator.validateNameLength(stationName);
        InputValidator.validateIsInLine(stationName);

        StationRepository.deleteStation(stationName);
    }

    public Station getStation(String stationName) {
        InputValidator.validateInput(stationName);
        InputValidator.validateNameLength(stationName);
        InputValidator.validateStationExists(stationName);

        return StationRepository.findStation(stationName)
                .orElseThrow();
    }

    public Station getLowerStation(Station upperStation, String lowerStation) {
        InputValidator.validateInput(lowerStation);
        InputValidator.validateNameLength(lowerStation);
        InputValidator.validateStationExists(lowerStation);
        InputValidator.validateDuplicateInput(upperStation, lowerStation);

        return StationRepository.findStation(lowerStation)
                .orElseThrow();
    }
}
