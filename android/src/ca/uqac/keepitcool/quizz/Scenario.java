package ca.uqac.keepitcool.quizz;

import java.util.ArrayList;
import java.util.List;

public class Scenario {
    private List<Question> questions;

    public Scenario() {
        this.questions = new ArrayList<Question>();
    }

    public Scenario addQuestion(Question q) {
        this.questions.add(this.questions.size(), q);
        return this;
    }

    private Question getQuestionAt(int index) {
        return this.questions.get(index);
    }
}
