package subway.view;

public enum Sentence {

    PREFIX("## "),
    INFO("[INFO] ");

    final String message;

    Sentence(String message) {
        this.message = message;
    }
}
