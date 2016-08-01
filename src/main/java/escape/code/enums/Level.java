package escape.code.enums;

import escape.code.utils.Constants;

import java.util.Arrays;

public enum Level {
    ZERO(0, Constants.DEMO_LEVEL_FXML_PATH),
    ONE(1, Constants.FIRST_LEVEL_FXML_PATH),
    TWO(2, Constants.SECOND_LEVEL_FXML_PATH);

    private int num;
    private String path;

    Level(int num, String constPath) {
        this.num = num;
        this.path = constPath;
    }

    private int getNum() {
        return this.num;
    }

    public String getPath() {
        return this.path;
    }

    public static Level getByNum(int num){
      return Arrays.stream(Level.values()).filter(level -> level.getNum() == num).findFirst().get();
    }
}
