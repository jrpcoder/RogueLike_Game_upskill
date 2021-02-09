package pt.upskills.projeto.objects;

import java.util.Scanner;

public class Score {
    private String playerName;
    private int score;

    public Score(String playerName, int score) {
        this.playerName = playerName;
        this.score = score;
    }

    public int getScore() {
        return score;
    }

    public String getPlayerName() {
        return playerName;
    }

    @Override
    public String toString() {
        return " " + getPlayerName() + " " + getScore();
    }
}
