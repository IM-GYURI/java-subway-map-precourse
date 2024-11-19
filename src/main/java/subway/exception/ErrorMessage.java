package subway.exception;

public enum ErrorMessage {
    INVALID_FILE_LOAD("파일을 읽는 중 오류가 발생하였습니다."),
    INVALID_INPUT("입력은 비어있을 수 없습니다."),
    INVALID_NAME_LENGTH("이름은 2글자 이상이어야 합니다."),
    DUPLICATE_STATION_NAME("이미 존재하는 역 이름입니다."),
    DUPLICATE_STATION_INPUT_FOR_LINE("상행 종점역과 하행 종점역은 같을 수 없습니다."),
    STATION_NOT_EXISTS("존재하지 않는 역 이름입니다."),
    LINE_NOT_EXISTS("존재하지 않는 노선 이름입니다."),
    DUPLICATE_LINE_NAME("이미 존재하는 노선 이름입니다."),
    STATIONS_SHORTAGE("노선은 2개 이상의 역을 포함해야 합니다."),
    INVALID_FEATURE("선택할 수 없는 기능입니다."),
    TOO_MANY_INVALID_INPUT("유효하지 않은 입력이 5회 반복되어 프로그램이 종료됩니다.");

    private static final String PREFIX = "[ERROR] ";

    final String message;

    ErrorMessage(final String message) {
        this.message = PREFIX + message;
    }

    @Override
    public String toString() {
        return message;
    }
}
