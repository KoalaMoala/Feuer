package ca.uqac.keepitcool.quizz.scenario;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ca.uqac.keepitcool.quizz.utils.Difficulty;
import ca.uqac.keepitcool.quizz.utils.Icon;
import ca.uqac.keepitcool.quizz.utils.Trigger;
import ca.uqac.keepitcool.quizz.utils.UserDecision;

public final class Situation {
    private Trigger trigger;
    private String description;
    private Map<UserDecision, Choice> choices;

    public Situation(Trigger trigger, String description) {
        this.trigger = trigger;
        this.description = description;
        this.choices = new HashMap<UserDecision, Choice>();
    }

    // ============================================================
    //                      SITUATION ADDITION
    // ============================================================

    public Situation addChoice(UserDecision userDecision, String label, Integer followUp, Icon icon) {
        this.choices.put(userDecision, new Choice(label, followUp, userDecision, icon));
        return this;
    }

    // ============================================================
    //                       GETTERS - CHOICES
    // ============================================================

    public int getChoicesCount() {
        return this.choices.size();
    }

    public List<Choice> getChoicesInRandomOrder() {
        Choice[] availableChoices = this.choices.values().toArray(new Choice[choices.size()]);
        List<Choice> choiceList = Arrays.asList(availableChoices);
        Collections.shuffle(choiceList);
        return choiceList;
    }

    public Integer getFollowUpFrom(UserDecision userDecision) {
        return this.choices.get(userDecision).getFollowUp();
    }

    // ============================================================
    //                         OTHER GETTERS
    // ============================================================

    public String getDescription() {
        return this.description;
    }

    public String getEndingType() {
        return this.trigger.name();
    }

    public Integer getDuration(Difficulty difficulty) {
        return this.trigger.getCountdown(difficulty);
    }

    public boolean countdownRequired() {
        return (this.trigger != Trigger.FAILURE && this.trigger != Trigger.SUCCESS);
    }
}
