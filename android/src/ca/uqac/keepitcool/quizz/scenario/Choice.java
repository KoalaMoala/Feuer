package ca.uqac.keepitcool.quizz.scenario;

import ca.uqac.keepitcool.quizz.utils.FancyColor;
import ca.uqac.keepitcool.quizz.utils.Icon;

public class Choice {
    private String label;
    private Icon icon;
    private FancyColor color;
    private int followUp;

    public Choice(String label, int followUp, FancyColor color, Icon icon) {
        this.label = label;
        this.followUp = followUp;
        this.icon = icon;
        this.color = color;
    }

    public String getLabel() {
        return this.label;
    }

    public String getIcon() {
        return this.icon.getUnicode();
    }

    public String getDefaultColor() {
        return this.color.getDefaultColor();
    }

    public String getFocusColor() {
        return this.color.getFocusColor();
    }

    public Integer getFollowUp() {
        return this.followUp;
    }
}
