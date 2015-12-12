package ca.uqac.keepitcool.quizz.utils;

// http://www.binaryhexconverter.com/hex-to-decimal-converter

public enum FancyColor {
    GREEN("#7ab800", "#9bd823"),
    RED("#b80600", "#d83e23");

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
}
