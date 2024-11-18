package subway.view;

import java.util.Scanner;

public class InputView {

    private final Scanner scanner;

    public InputView(Scanner scanner) {
        this.scanner = scanner;
    }

    public String askFeature() {
        System.out.println(Sentence.PREFIX.message + "원하는 기능을 선택하세요.");
        return scanner.nextLine().trim();
    }
}
