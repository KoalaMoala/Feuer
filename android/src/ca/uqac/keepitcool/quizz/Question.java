package ca.uqac.keepitcool.quizz;

public class Question {
    private String situation;
    private int duration;
    private boolean answer;

    public Question(int duration, boolean answer, String situation) {
        this.duration = duration;
        this.answer = answer;
        this.situation = situation;
    }

    public String getSituation() {
        return situation;
    }

    public Question setSituation(String situation) {
        this.situation = situation;
        return this;
    }

    public int getDuration() {
        return duration;
    }

    public Question setDuration(int duration) {
        this.duration = duration;
        return this;
    }

    public boolean isAnswer() {
        return answer;
    }

    public Question setAnswer(boolean answer) {
        this.answer = answer;
        return this;
    }
}
