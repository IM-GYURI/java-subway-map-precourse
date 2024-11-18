package subway.view;

import java.util.List;
import subway.domain.Line;

public class OutputView {

    public void printMain() {
        System.out.println(Sentence.PREFIX.message + "메인 화면");
        System.out.println(MainFeature.SELECT_ONE.message + ". 역 관리");
        System.out.println(MainFeature.SELECT_TWO.message + ". 노선 관리");
        System.out.println(MainFeature.SELECT_THREE.message + ". 구간 관리");
        System.out.println(MainFeature.SELECT_FOUR.message + ". 지하철 노선도 출력");
        System.out.println(MainFeature.QUIT.message + ". 종료" + System.lineSeparator());
    }

    public void printSubwayLinesInformation(List<Line> lines) {
        System.out.println(System.lineSeparator() + Sentence.PREFIX.message + "지하철 노선도");
        lines.forEach(line -> {
            String sentence = formatLineAndStations(line);
            System.out.println(sentence + System.lineSeparator());
        });
    }

    public static void printError(String errorMessage) {
        System.out.println(System.lineSeparator() + errorMessage + System.lineSeparator());
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
