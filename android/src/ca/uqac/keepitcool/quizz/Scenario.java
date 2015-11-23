package ca.uqac.keepitcool.quizz;

import java.util.ArrayList;
import java.util.List;

public class Scenario {
    private List<Situation> scenario;
    private int currentSituation;

    public Scenario() {
        this.scenario = new ArrayList<Situation>();
    }

    public void addStartingSituation(Situation s) {
        this.currentSituation = this.addSituation(s);
    }

    public int addSituation(Situation s) {
        int index = this.scenario.size();
        this.scenario.add(index, s);
        return index;
    }

    public int addSituation(Trigger trigger, String text) {
        int index = this.scenario.size();
        this.scenario.add(index, new Situation(trigger, text));
        return index;
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
