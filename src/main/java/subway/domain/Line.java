package subway.domain;

import java.util.LinkedList;
import java.util.List;

public class Line {
    private String name;
    private LinkedList<Station> stations;

    public Line(String name, LinkedList<Station> stations) {
        this.name = name;
        this.stations = stations;
    }

    public String getName() {
        return name;
    }

    public List<Station> getStations() {
        return stations.stream()
                .toList();
    }
}
