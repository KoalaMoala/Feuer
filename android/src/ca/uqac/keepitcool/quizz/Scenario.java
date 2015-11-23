package ca.uqac.keepitcool.quizz;

import java.util.ArrayList;
import java.util.List;

public class Scenario {
    private List<Situation> scenario;
    private int currentSituation;

    public Scenario() {
        this.scenario = new ArrayList<Situation>();
    }

    public void addStartingSituation(int duration, String text, String firstChoice, int firstChoiceFollowUp, String secondChoice, int secondChoiceFollowUp) {
        this.currentSituation = this.addSituation(duration,text,firstChoice,firstChoiceFollowUp,secondChoice,secondChoiceFollowUp);
    }

    public int addSituation(int duration, String text, String firstChoice, int firstChoiceFollowUp, String secondChoice, int secondChoiceFollowUp) {
        int index = this.scenario.size();
        Situation s = new Situation(duration, text)
                .setFirstChoice(firstChoice, firstChoiceFollowUp)
                .setSecondChoice(secondChoice, secondChoiceFollowUp);
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

    public Situation getNextSituation(Choice choice) {
        Integer followUp = null;
        Situation s = this.scenario.get(this.currentSituation);

        if(Choice.FIRST == choice) {
            followUp = this.scenario.get(this.currentSituation).getFirstChoiceFollowUp();
        } else {
            followUp = this.scenario.get(this.currentSituation).getSecondChoiceFollowUp();
        }

        this.currentSituation = followUp;
        return this.scenario.get(followUp);
    }
}
