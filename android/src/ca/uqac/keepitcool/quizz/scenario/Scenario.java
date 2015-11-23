package ca.uqac.keepitcool.quizz.scenario;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ca.uqac.keepitcool.quizz.UserChoice;

public class Scenario {
    private Map<Integer,Situation> scenario;
    private int currentSituation;

    Scenario() {
        this.scenario = new HashMap<Integer,Situation>();
    }

    Scenario addStartingSituation(Integer key, Trigger trigger, String text) {
        this.addSituation(key, trigger, text);
        this.currentSituation = key;
        return this;
    }

    Scenario addSituation(Integer key, Trigger trigger, String text) {
        this.scenario.put(key, new Situation(trigger, text));
        return this;
    }

    Scenario addChoiceToSituation(int key, UserChoice choice, String label, int followUp, FancyColor color, Icon icon) {
        if(UserChoice.FIRST == choice) {
            this.scenario.get(key).setFirstChoice(label, followUp, color, icon);
        } else {
            this.scenario.get(key).setSecondChoice(label, followUp, color, icon);
        }
        return this;
    }

    public Situation getStartingSituation() {
        return this.scenario.get(this.currentSituation);
    }

    public Situation getNextSituation(UserChoice userChoice) {
        Integer followUp = null;
        Situation s = this.scenario.get(this.currentSituation);

        if(UserChoice.FIRST == userChoice) {
            followUp = this.scenario.get(this.currentSituation).getFirstChoiceFollowUp();
        } else {
            followUp = this.scenario.get(this.currentSituation).getSecondChoiceFollowUp();
        }

        this.currentSituation = followUp;
        return this.scenario.get(followUp);
    }
}
