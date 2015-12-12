package ca.uqac.keepitcool.quizz.scenario;

import java.util.HashMap;
import java.util.Map;

import ca.uqac.keepitcool.quizz.utils.FancyColor;
import ca.uqac.keepitcool.quizz.utils.Icon;
import ca.uqac.keepitcool.quizz.utils.Trigger;
import ca.uqac.keepitcool.quizz.utils.UserDecision;

public class Scenario {
    private Map<Integer,Situation> scenario;
    private int currentSituation;

    Scenario() {
        this.scenario = new HashMap<Integer,Situation>();
    }

    Scenario addStartingSituation(int key, Situation s) {
        this.scenario.put(key, s);
        this.currentSituation = key;
        return this;
    }

    Scenario addStartingSituation(Integer key, Trigger trigger, String text) {
        return this.addStartingSituation(key, new Situation(trigger,text));
    }

    Scenario addSituation(Integer key, Situation s) {
        this.scenario.put(key, s);
        return this;
    }

    Scenario addSituation(Integer key, Trigger trigger, String text) {
        return this.addSituation(key, new Situation(trigger, text));
    }

    Scenario addChoiceToSituation(int key, UserDecision userDecision, String label, int followUp, FancyColor color, Icon icon) {
        this.scenario.get(key).addChoice(userDecision, label, followUp, color, icon);
        return this;
    }

    public Situation getStartingSituation() {
        return this.scenario.get(this.currentSituation);
    }

    public Situation getNextSituation(UserDecision userDecision) {
        Integer followUp = null;
        Situation s = this.scenario.get(this.currentSituation);
        followUp = s.getFollowUpFrom(userDecision);
        this.currentSituation = followUp;
        return this.scenario.get(followUp);
    }
}
