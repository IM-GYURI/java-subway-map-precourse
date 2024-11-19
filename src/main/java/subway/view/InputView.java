package subway.view;

import java.util.Scanner;

public class InputView {

    private static final String PREFIX = "## ";
    private final Scanner scanner;

    public InputView(Scanner scanner) {
        this.scanner = scanner;
    }

    public String askFeature() {
        System.out.println(PREFIX + "원하는 기능을 선택하세요.");
        return scanner.nextLine().trim();
    }

    public String askStationEnroll() {
        System.out.println(System.lineSeparator()
                + PREFIX + "등록할 역 이름을 입력하세요.");
        return scanner.nextLine().trim();
    }

    public String askStationDelete() {
        System.out.println(System.lineSeparator()
                + PREFIX + "삭제할 역 이름을 입력하세요.");
        return scanner.nextLine().trim();
    }

    public String askLineEnroll() {
        System.out.println(System.lineSeparator()
                + PREFIX + "등록할 노선 이름을 입력하세요.");
        return scanner.nextLine().trim();
    }

    public String askUpperStation() {
        System.out.println(System.lineSeparator()
                + PREFIX + "등록할 노선의 상행 종점역 이름을 입력하세요.");
        return scanner.nextLine().trim();
    }

    public String askLowerStation() {
        System.out.println(System.lineSeparator()
                + PREFIX + "등록할 노선의 하행 종점역 이름을 입력하세요.");
        return scanner.nextLine().trim();
    }

    public String askLineDelete() {
        System.out.println(System.lineSeparator()
                + PREFIX + "삭제할 노선 이름을 입력하세요.");
        return scanner.nextLine().trim();
    }

    public String askLineNameOfSection() {
        System.out.println(System.lineSeparator()
                + PREFIX + "노선을 입력하세요.");
        return scanner.nextLine().trim();
    }

    public String askStationNameOfSection() {
        System.out.println(System.lineSeparator()
                + PREFIX + "역이름을 입력하세요.");
        return scanner.nextLine().trim();
    }

    public String askOrderOfSection() {
        System.out.println(System.lineSeparator()
                + PREFIX + "순서를 입력하세요.");
        return scanner.nextLine().trim();
    }

    public String askDeleteLineOfSection() {
        System.out.println(System.lineSeparator()
                + PREFIX + "삭제할 구간의 노선을 입력하세요.");
        return scanner.nextLine().trim();
    }

    public String askDeleteStationOfSection() {
        System.out.println(System.lineSeparator()
                + PREFIX + "삭제할 구간의 역을 입력하세요.");
        return scanner.nextLine().trim();
    }
}
