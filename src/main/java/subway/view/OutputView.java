package subway.view;

import java.util.List;
import subway.domain.Line;
import subway.domain.Station;
import subway.repository.StationRepository;

public class OutputView {

    public void printMain() {
        System.out.println(Sentence.PREFIX.message + "메인 화면");
        System.out.println(MainFeature.SELECT_ONE.message + ". 역 관리");
        System.out.println(MainFeature.SELECT_TWO.message + ". 노선 관리");
        System.out.println(MainFeature.SELECT_THREE.message + ". 구간 관리");
        System.out.println(MainFeature.SELECT_FOUR.message + ". 지하철 노선도 출력");
        System.out.println(MainFeature.QUIT.message + ". 종료" + System.lineSeparator());
    }

    public void printStationManagement() {
        System.out.println(System.lineSeparator()
                + Sentence.PREFIX.message + "역 관리 화면");
        System.out.println(StationFeature.SELECT_ONE.message + ". 역 등록");
        System.out.println(StationFeature.SELECT_TWO.message + ". 역 삭제");
        System.out.println(StationFeature.SELECT_THREE.message + ". 역 조회");
        System.out.println(StationFeature.BACK.message + ". 돌아가기" + System.lineSeparator());
    }

    public void printEnrollStationSuccess() {
        System.out.println(System.lineSeparator()
                + Sentence.INFO.message + "지하철 역이 등록되었습니다.");
    }

    public void printDeleteStationSuccess() {
        System.out.println(System.lineSeparator()
                + Sentence.INFO.message + "지하철 역이 삭제되었습니다.");
    }

    public void printStations() {
        List<Station> stations = StationRepository.stations();
        StringBuilder sentences = new StringBuilder();
        stations.forEach(station -> {
            sentences.append(formatStation(station))
                    .append(System.lineSeparator());
        });

        System.out.println(System.lineSeparator()
                + Sentence.PREFIX.message + "역 목록");
        System.out.print(sentences);
    }

    public void printSubwayLinesInformation(List<Line> lines) {
        System.out.println(System.lineSeparator()
                + Sentence.PREFIX.message + "지하철 노선도");
        lines.forEach(line -> {
            String sentence = formatLineAndStations(line);
            System.out.println(sentence);
        });
    }

    public static void printError(String errorMessage) {
        System.out.println(System.lineSeparator()
                + errorMessage + System.lineSeparator());
    }

    private String formatStation(Station station) {
        return Sentence.INFO.message + station.getName();
    }

    private String formatLineAndStations(Line line) {
        String stationsInfo = line.getStations().stream()
                .map(station -> Sentence.INFO.message + station.getName())
                .reduce((s1, s2) -> s1 + System.lineSeparator() + s2)
                .orElse("");

        return String.format("%s%s%n%s---%n%s",
                Sentence.INFO.message,
                line.getName(),
                Sentence.INFO.message,
                stationsInfo);
    }
}
