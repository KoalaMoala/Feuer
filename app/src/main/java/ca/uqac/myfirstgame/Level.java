package ca.uqac.myfirstgame;

import java.io.Serializable;

/**
 * Created by Pomme on 10/26/2015.
 */
public class Level implements Serializable{
    public int idLevel;
    public String name;
    public String description;
    public int score;

    public int getIdLevel() {
        return idLevel;
    }

    public void setIdLevel(int idLevel) {
        this.idLevel = idLevel;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
