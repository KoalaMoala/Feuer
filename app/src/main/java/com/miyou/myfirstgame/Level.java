package com.miyou.myfirstgame;

import java.io.Serializable;

public class Level implements Serializable{
    private int idLevel;
    private String name;
    private String description;
    private int score;

    public Level(int idLevel, String name, String description) {
        this.idLevel = idLevel;
        this.name = name;
        this.description = description;
        this.score = 0;
    }

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
