package ca.uqac.keepitcool.quizz.scenario;

public enum Trigger {
    SUCCESS(null, null, null),
    FAILURE(null, null, null),
    LONG(30,25,20),
    MEDIUM(20,15,10),
    SHORT(15,10,7);

    private Integer hardCountdown;
    private Integer mediumCountdown;
    private Integer easyCountdown;

    Trigger(Integer easyCountdown, Integer mediumCountdown, Integer hardCountdown) {
        this.easyCountdown = easyCountdown;
        this.mediumCountdown = mediumCountdown;
        this.hardCountdown = hardCountdown;
    }

    public Integer getCountdownOnEasyDifficulty() {
        return this.easyCountdown;
    }

    public Integer getCountdownOnMediumDifficulty() {
        return this.mediumCountdown;
    }

    public Integer getCountdownOnHardDifficulty() {
        return this.hardCountdown;
    }
}
