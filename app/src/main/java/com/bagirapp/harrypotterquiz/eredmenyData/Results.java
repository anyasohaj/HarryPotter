package com.bagirapp.harrypotterquiz.eredmenyData;

public class Results {
    private String date;
    private int level;
    private int points;

    public Results(String date, int level, int points){
        this.date = date;
        this.level = level;
        this.points = points;
    }

    public String getDate() {
        return date;
    }

    public int getLevel() {
        return level;
    }

    public int getPoints() {
        return points;
    }
}
