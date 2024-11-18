package subway.domain;

import java.util.LinkedList;

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

}
