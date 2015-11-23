package ca.uqac.keepitcool.quizz.scenario;

// Unicode from http://fortawesome.github.io/Font-Awesome/cheatsheet/

public enum Icon {
    FIRE("\uf06d"),
    ELECTRICITY("\uf0e7"),
    EXTINGUISHER("\uf134"),
    SUCCESS("\uf05d"),
    ERROR("\uf05c"),
    DOWN("\uf01a"),
    UP("\uf01b"),
    EYE("\uf06e"),
    SLEEP("\uf236"),
    STANDUP("\uf183"),
    ENTER("\uf062"),
    EXIT("\uf08e"),
    HAND("\uf256"),
    UNLOCK("\uf09c");

    private String unicode;

    Icon(String unicode) {
        this.unicode = unicode;
    }

    public String getUnicode() {
        return this.unicode;
    }
}
