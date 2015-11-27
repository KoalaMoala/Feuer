package ca.uqac.keepitcool.quizz.scenario;

public final class Situation {
    private Trigger trigger;
    private String description;
    private Choice firstChoice = null;
    private Choice secondChoice = null;

    public Situation(Trigger trigger, String description) {
        this.trigger = trigger;
        this.description = description;
    }

    public String getDescription() {
        return this.description;
    }

    public Integer getDuration(Difficulty difficulty) {
        return this.trigger.getCountdown(difficulty);
    }

    public boolean countdownRequired() {
        return (this.trigger != Trigger.FAILURE && this.trigger != Trigger.SUCCESS);
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

    public Trigger getTrigger() {
        return trigger;
    }
}
