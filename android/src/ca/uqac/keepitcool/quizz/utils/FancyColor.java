package ca.uqac.keepitcool.quizz.utils;

import java.util.Arrays;
import java.util.Random;

public enum FancyColor {
    BLUE("#3B5998", "#5474B8"),
    GREEN("#7AB800", "#9BD823"),
    GREY("#3B414F", "#667189"),
    LIGHTBLUE("#55ACEE", "#83CAFF"),
    ORANGE("#FF8800", "#FFA641"),
    PURPLE("#712FF0","#9F71F9"),
    RED("#B80600", "#D83E23");

    private static final Random RANDOM = new Random();
    private String defaultColor;
    private String focusColor;

    FancyColor(String defaultColor, String focusColor) {
        this.defaultColor = defaultColor;
        this.focusColor = focusColor;
    }

    public String getDefaultColor() {
        return this.defaultColor;
    }

    public String getFocusColor() {
        return this.focusColor;
    }

    public static FancyColor getRandomColor() {
        FancyColor[] constants = FancyColor.class.getEnumConstants();
        int x = RANDOM.nextInt(constants.length);
        return constants[x];
    }

    public static FancyColor[] getRandomColors(int size) {
        FancyColor[] constants = FancyColor.class.getEnumConstants();
        FancyColor[] results = new FancyColor[2];

        for(int count=0; count < size; count++) {
            FancyColor currentColor = constants[RANDOM.nextInt(constants.length)];
            while (Arrays.asList(results).contains(currentColor)) {
                currentColor = constants[RANDOM.nextInt(constants.length)];
            }
            results[count] = currentColor;
        }

        return results;
    }
}
