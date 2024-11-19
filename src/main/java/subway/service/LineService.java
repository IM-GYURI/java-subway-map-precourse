package subway.service;

import java.util.LinkedList;
import subway.constants.StationLineFeature;
import subway.domain.Line;
import subway.domain.Station;
import subway.exception.InputValidator;
import subway.repository.LineRepository;

public class LineService {

    public StationLineFeature selectLineFeature(String selectedFeature) {
        InputValidator.validateInput(selectedFeature);

        return StationLineFeature.getFeatureFromInput(selectedFeature);
    }

    public String getValidLineName(String lineName) {
        InputValidator.validateInput(lineName);
        InputValidator.validateNameLength(lineName);

        return lineName;
    }

    public Line getLine(String lineName) {
        InputValidator.validateInput(lineName);
        InputValidator.validateNameLength(lineName);
        InputValidator.validateLineExists(lineName);

        return LineRepository.findLine(lineName)
                .orElseThrow();
    }

    public void enrollLine(String lineName, Station upperStation, Station lowerStation) {
        LinkedList<Station> stations = new LinkedList<>();
        stations.add(upperStation);
        stations.add(lowerStation);

        Line line = new Line(lineName, stations);
        LineRepository.addLine(line);
    }
}
