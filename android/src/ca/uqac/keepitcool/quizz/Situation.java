package ca.uqac.keepitcool.quizz;

public final class Situation {
    private Trigger trigger;
    private String description;
    private Integer duration = null;
    private Choice firstChoice = null;
    private Choice secondChoice = null;

    public Situation(Integer duration, String description) {
        this.duration = duration;
        this.description = description;
    }

    public Situation(Trigger trigger, String description) {
        this.trigger = trigger;
        this.description = description;
    }

    public String getDescription() {
        return this.description;
    }

    public Integer getDuration() {
        return this.duration;
    }

    public boolean countdownRequired() {
        return this.duration != null;
    }

    public Choice getFirstChoice() {
        return this.firstChoice;
    }

    public int getFirstChoiceFollowUp() {
        return this.firstChoice.getFollowUp();
    }

    public Situation setFirstChoice(String label, Integer followUp, FancyColor color, Icon icon) {
        this.firstChoice = new Choice(label, followUp, color, icon);
        return this;
    }

    public Choice getSecondChoice() {
        return this.secondChoice;
    }

    public int getSecondChoiceFollowUp() {
        return this.secondChoice.getFollowUp();
    }

    public Situation setSecondChoice(String label, Integer followUp, FancyColor color, Icon icon) {
        this.secondChoice = new Choice(label, followUp, color, icon);
        return this;
    }
}
