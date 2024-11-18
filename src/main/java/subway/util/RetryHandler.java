package subway.util;

import java.util.function.Supplier;
import subway.exception.ErrorMessage;
import subway.view.OutputView;

public class RetryHandler {
    private static final int MAX_ATTEMPTS = 5;

    public static <T> T getValidInput(final Supplier<T> supplier) {
        int attempts = 0;
        while (attempts < MAX_ATTEMPTS) {
            try {
                return supplier.get();
            } catch (IllegalArgumentException e) {
                attempts++;
                OutputView.printError(e.getMessage());
            }
        }
        throw new IllegalArgumentException(ErrorMessage.TOO_MANY_INVALID_INPUT.toString());
    }
}
