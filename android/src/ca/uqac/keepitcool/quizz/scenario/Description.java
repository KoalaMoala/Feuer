package ca.uqac.keepitcool.quizz.scenario;

public class Description {
    private final String name;
    private final String description;

    public Description(String name, String shortDescription) {
        this.name = name;
        this.description = shortDescription;
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }
}
