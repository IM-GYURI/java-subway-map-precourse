package subway.view;

import java.util.List;
import java.util.stream.Collectors;
import subway.domain.Line;
import subway.domain.Station;

public class OutputView {

    public static void printError(String errorMessage) {
        System.out.println(System.lineSeparator()
                + errorMessage + System.lineSeparator());
    }

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
        System.out.println(StationLineFeature.SELECT_ONE.message + ". 역 등록");
        System.out.println(StationLineFeature.SELECT_TWO.message + ". 역 삭제");
        System.out.println(StationLineFeature.SELECT_THREE.message + ". 역 조회");
        System.out.println(StationLineFeature.BACK.message + ". 돌아가기" + System.lineSeparator());
    }

    public void printEnrollStationSuccess() {
        System.out.println(System.lineSeparator()
                + Sentence.INFO.message + "지하철 역이 등록되었습니다.");
    }

    public void printDeleteStationSuccess() {
        System.out.println(System.lineSeparator()
                + Sentence.INFO.message + "지하철 역이 삭제되었습니다.");
    }

    public void printStations(List<Station> stations) {
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
        String result = lines.stream()
                .map(this::formatLineAndStations)
                .collect(Collectors.joining(System.lineSeparator().repeat(2)));
        System.out.println(result);
    }

    public void printLineManagement() {
        System.out.println(System.lineSeparator()
                + Sentence.PREFIX.message + "노선 관리 화면");
        System.out.println(StationLineFeature.SELECT_ONE.message + ". 노선 등록");
        System.out.println(StationLineFeature.SELECT_TWO.message + ". 노선 삭제");
        System.out.println(StationLineFeature.SELECT_THREE.message + ". 노선 조회");
        System.out.println(StationLineFeature.BACK.message + ". 돌아가기" + System.lineSeparator());
    }

    public void printEnrollLineSuccess() {
        System.out.println(System.lineSeparator()
                + Sentence.INFO.message + "지하철 노선이 등록되었습니다.");
    }

    public void printDeleteLineSuccess() {
        System.out.println(System.lineSeparator()
                + Sentence.INFO.message + "지하철 노선이 삭제되었습니다.");
    }

    public void printLines(List<Line> lines) {
        System.out.println(System.lineSeparator()
                + Sentence.PREFIX.message + "노선 목록");
        lines.forEach(line -> {
            String sentence = formatLine(line);
            System.out.println(sentence);
        });
    }

    public void printSectionManagement() {
        System.out.println(System.lineSeparator()
                + Sentence.PREFIX.message + "구간 관리 화면");
        System.out.println(SectionFeature.SELECT_ONE.message + ". 구간 등록");
        System.out.println(SectionFeature.SELECT_TWO.message + ". 구간 삭제");
        System.out.println(SectionFeature.BACK.message + ". 돌아가기" + System.lineSeparator());
    }

    public void printEnrollSectionSuccess() {
        System.out.println(System.lineSeparator()
                + Sentence.INFO.message + "구간이 등록되었습니다.");
    }

    public void printDeleteSectionSuccess() {
        System.out.println(System.lineSeparator()
                + Sentence.INFO.message + "구간이 삭제되었습니다.");
    }


    private String formatStation(Station station) {
        return Sentence.INFO.message + station.getName();
    }

    private String formatLine(Line line) {
        return Sentence.INFO.message + line.getName();
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
