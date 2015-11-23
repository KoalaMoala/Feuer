package ca.uqac.keepitcool.quizz;

public final class Situation {
    private Trigger trigger;
    private String description;
    private Integer duration = null;
    private String firstChoice = null;
    private Integer firstChoiceFollowUp = null;
    private String secondChoice = null;
    private Integer secondChoiceFollowUp = null;

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

    public String getFirstChoice() {
        return this.firstChoice;
    }

    public int getFirstChoiceFollowUp() {
        return this.firstChoiceFollowUp;
    }

    public Situation setFirstChoice(String firstChoice, Integer firstChoiceFollowUp) {
        this.firstChoice = firstChoice;
        this.firstChoiceFollowUp = firstChoiceFollowUp;
        return this;
    }

    public String getSecondChoice() {
        return this.secondChoice;
    }

    public int getSecondChoiceFollowUp() {
        return this.secondChoiceFollowUp;
    }

    public Situation setSecondChoice(String secondChoice, Integer secondChoiceFollowup) {
        this.secondChoice = secondChoice;
        this.secondChoiceFollowUp = secondChoiceFollowup;
        return this;
    }
}
